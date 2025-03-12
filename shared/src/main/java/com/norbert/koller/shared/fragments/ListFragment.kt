package com.norbert.koller.shared.fragments


import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.pdf.models.ListItem
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.view.ContextThemeWrapper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import androidx.core.view.iterator
import androidx.core.view.marginEnd
import androidx.core.view.updatePadding
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.customviews.SuperCoolRecyclerView
import com.norbert.koller.shared.data.FilterConfigData
import com.norbert.koller.shared.data.ListCardItem
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListCardStaticBsdfFragment
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.viewmodels.ListApiComplexViewModel
import com.norbert.koller.shared.viewmodels.ListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.ArrayList

data class ButtonParameters(val text: String, val iconID : Int = R.drawable.add, val onClick: () -> Unit)

abstract class ListFragment() : SearchFragment() {

    var snackbar : Snackbar? = null

    fun getBaseViewModel() : ListApiComplexViewModel {
        return viewModel as ListApiComplexViewModel
    }

    lateinit var scRecyclerView : SuperCoolRecyclerView
    private var recyclerAdapter : ApiRecyclerAdapter? = null

    var chipHolderAnimationDuration : Long = 0

    abstract fun getPagingSource() : PagingSourceWithSeparator

    abstract fun getRecyclerAdapter() : ApiRecyclerAdapter


    override fun assignViewModel() {
        viewModel = ViewModelProvider(this)[ListApiComplexViewModel::class.java]

        if(arguments != null) {
            val filters: FilterConfigData? = arguments?.getParcelable("filters")
            if(filters != null){
                getBaseViewModel().filters = filters.filters
            }
        }

        getBaseViewModel().pagingSource = {
            getPagingSource()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        postponeEnterTransition()
        (view as ViewGroup).viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        recyclerAdapter = getRecyclerAdapter()
        recyclerAdapter!!.viewModel = getBaseViewModel()

        super.onViewCreated(view, savedInstanceState)

        scRecyclerView = SuperCoolRecyclerView(requireContext())
        binding.root.addView(scRecyclerView)
        val params = CoordinatorLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        params.behavior = AppBarLayout.ScrollingViewBehavior()
        scRecyclerView.layoutParams = params

        scRecyclerView.appBar = binding.appBar

        if(savedInstanceState != null){
            if(getBaseViewModel().isRequestModeRefresh){
                when(getBaseViewModel().state){
                    ApiHelper.STATE_LOADING ->{
                        scRecyclerView.binding.srl.isRefreshing = true
                    }
                    ApiHelper.STATE_ERROR ->{
                        createSnackBar()
                    }
                }
            }
            else{
                when(getBaseViewModel().state){
                    ApiHelper.STATE_LOADING ->{
                        if(recyclerAdapter!!.snapshot().size <= 1) {
                            scRecyclerView.binding.srl.isEnabled = false
                        }
                    }
                    ApiHelper.STATE_NONE ->{
                        scRecyclerView.binding.srl.isEnabled = true
                    }
                }
            }
        }

        getBaseViewModel().onRefreshError = {
            scRecyclerView.binding.srl.isRefreshing = false
            createSnackBar()
        }

        getBaseViewModel().onRefreshSuccess = {
            scRecyclerView.binding.recyclerView.scrollToPosition(0)
            scRecyclerView.binding.srl.isRefreshing = false
            snackbar?.dismiss()

        }

        getBaseViewModel().onAppendLoading = {
            if(recyclerAdapter!!.snapshot().size <= 1){
                scRecyclerView.binding.srl.isEnabled = false
            }
            Handler(Looper.getMainLooper()).post {
                recyclerAdapter!!.notifyItemChanged(recyclerAdapter!!.itemCount-1)
                snackbar?.dismiss()
            }
        }

        getBaseViewModel().onAppendError = {
            Handler(Looper.getMainLooper()).post {
                recyclerAdapter!!.notifyItemChanged(recyclerAdapter!!.itemCount-1)
                snackbar?.dismiss()
            }
        }

        getBaseViewModel().onAppendSuccess = {
            scRecyclerView.binding.srl.isEnabled = true

            recyclerAdapter!!.notifyItemRemoved(recyclerAdapter!!.itemCount)
            recyclerAdapter!!.notifyItemChanged(recyclerAdapter!!.itemCount -1, Object())

        }

        scRecyclerView.setRecyclerAdapter(recyclerAdapter!!)

        lifecycleScope.launch {
            getBaseViewModel().pagingData.collectLatest { pagingData ->
                recyclerAdapter!!.submitData(pagingData)
            }
        }

        scRecyclerView.binding.srl.setOnRefreshListener {
            recyclerAdapter!!.seemlessRefresh()
            scRecyclerView.binding.recyclerView.scrollToPosition(0)
        }


        getBaseViewModel().onChipsChanged = {
            recyclerAdapter!!.fullRefresh()
        }
    }

    fun createSnackBar(){
        snackbar = ApiHelper.createSnackBar(requireContext()){
            recyclerAdapter!!.refresh()
            scRecyclerView.binding.srl.isRefreshing = true
        }
    }

    override fun onStop() {
        super.onStop()

        snackbar?.dismiss()

    }

    fun addButton(text : String, onClick: () -> Unit) {
         addButtons(ButtonParameters(text, onClick = onClick))
    }


    fun addButtons(vararg buttonParams : ButtonParameters) {

        var viewToAddButtonWidth = Int.MAX_VALUE
        var viewToAddButton : ViewGroup = binding.lyParameters
        if(buttonParams.count() > 1){
            val linearLayout  = LinearLayout(requireContext())
            linearLayout.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.gravity = Gravity.CENTER
            binding.lyParameters.addView(linearLayout)
            viewToAddButton = linearLayout
            binding.lyParameters.width

        }

        binding.lyParameters.post{

            viewToAddButtonWidth = viewToAddButton.width

            var fit = true
            var totalButtonWidth = 0

            var listCardItems : kotlin.collections.ArrayList<ListCardItem> = kotlin.collections.arrayListOf<ListCardItem>()

            for (i in buttonParams.indices){

                val currentButtonParam = buttonParams[i]

                if(fit == true) {
                    var style = R.style.ButtonTonalIcon
                    var textColorID = com.google.android.material.R.attr.colorOnSecondaryContainer
                    var backgroundColor =
                        requireContext().getAttributeColor(com.google.android.material.R.attr.colorSecondaryContainer)
                    if (i > 0) {
                        style = R.style.ButtonOutlineIcon
                        textColorID = com.google.android.material.R.attr.colorPrimary
                        backgroundColor = 0
                    }
                    val button =
                        MaterialButton(ContextThemeWrapper(requireContext() as MainActivity, style))
                    button.setBackgroundColor(backgroundColor)
                    val textColor = requireContext().getAttributeColor(textColorID)
                    button.setTextColor(textColor)
                    button.iconTint = button.textColors
                    button.text = currentButtonParam.text
                    button.icon =
                        AppCompatResources.getDrawable(requireContext(), currentButtonParam.iconID)
                    button.setPaddingRelative(
                        ApplicationManager.convertDpToPixel(
                            16,
                            requireContext()
                        ), button.paddingTop, button.paddingEnd, button.paddingBottom
                    )
                    val params = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    val textContainer =
                        resources.getDimensionPixelSize(R.dimen.text_container_margin)
                    params.setMargins(textContainer, textContainer, textContainer, textContainer)
                    params.gravity = Gravity.CENTER
                    button.layoutParams = params
                    viewToAddButton.addView(button)


                    viewToAddButton.measure(
                        View.MeasureSpec.UNSPECIFIED,
                        View.MeasureSpec.UNSPECIFIED
                    )

                    totalButtonWidth += button.measuredWidth + button.marginEnd * 2

                    Log.d("totalButtonWidth", totalButtonWidth.toString())
                    Log.d("viewToAddButton", viewToAddButtonWidth.toString())

                    if (buttonParams.count() - 1 > i && (viewToAddButtonWidth < totalButtonWidth + button.measuredHeight) || viewToAddButtonWidth < totalButtonWidth) {
                        fit = false
                        button.iconPadding = 0
                        button.updatePadding(left = 0, right = 0)
                        button.text = null
                        button.setIconResource(R.drawable.more_thick)
                        button.width = button.measuredHeight
                        listCardItems.add(ListCardItem(currentButtonParam.text, null, AppCompatResources.getDrawable(requireContext(), currentButtonParam.iconID), currentButtonParam.onClick))
                    }
                    else{
                        button.setOnClickListener {
                            currentButtonParam.onClick.invoke()
                        }
                        getMainActivity().onEditModeChange = {
                            button.isEnabled = !it
                            button.alpha = if (it) 0.25f else 1f
                        }
                    }
                }
                else{
                    listCardItems.add(ListCardItem(currentButtonParam.text, null, AppCompatResources.getDrawable(requireContext(), currentButtonParam.iconID), currentButtonParam.onClick))
                }

            }

            if(fit == false){
                val dialog = ListCardStaticBsdfFragment().setup((context as AppCompatActivity), listCardItems as kotlin.collections.ArrayList<com.norbert.koller.shared.data.ListItem>?)
                viewToAddButton.getChildAt(viewToAddButton.childCount-1).setOnClickListener { dialog.show(parentFragmentManager, ListBsdfFragment.TAG) }
            }
        }
    }

    fun addSearchbar(filterName : String){
        val surfaceColor = requireContext().getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerLow)

        val card = MaterialCardView(requireContext())
        val searchBar = SearchView(requireContext())
        val linearLayout = LinearLayout(requireContext())
        val closeButton = MaterialButton(ContextThemeWrapper(requireContext() as MainActivity, R.style.TextButton))
        closeButton.setBackgroundColor(surfaceColor)
        closeButton.cornerRadius = 0

        card.setCardBackgroundColor(Color.TRANSPARENT)
        card.strokeWidth = ApplicationManager.convertDpToPixel(1, requireContext())
        card.strokeColor = searchBar.cardBackgroundColor.defaultColor
        searchBar.radius = 0f

        val textColor = searchBar.getEditText().textColors.defaultColor

        (binding.textFilters.parent as ViewGroup).removeView(binding.textFilters)

        closeButton.text = getString(R.string.clear_filters)
        closeButton.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.close)

        closeButton.setTextColor(textColor)
        closeButton.iconTint = closeButton.textColors

        linearLayout.orientation = VERTICAL

        card.preventCornerOverlap = false
        val layoutParams = FlexboxLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val cardMarginLayoutParams = FlexboxLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        cardMarginLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.text_container_margin)
        cardMarginLayoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.spacing)
        cardMarginLayoutParams.maxWidth = (binding.chipsFilter.layoutParams as FlexboxLayout.LayoutParams).maxWidth
        val chipMarginLayoutParams = FlexboxLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val margin = resources.getDimensionPixelSize(R.dimen.card_padding)
        chipMarginLayoutParams.setMargins(margin,margin,margin,margin)
        binding.chipsFilter.layoutParams = chipMarginLayoutParams

        if(getBaseViewModel().filtersShown.value == false) {
            var anyOfHas = false
            for (chip in binding.chipsFilter.children) {
                chip as Chip
                if (chip.isChecked) {
                    anyOfHas = true
                }

                chip.measure(WRAP_CONTENT, WRAP_CONTENT)
                chip.layoutParams.height = chip.measuredHeight
            }
            getBaseViewModel().filtersShown.value = anyOfHas
        }

        linearLayout.layoutParams = layoutParams
        closeButton.layoutParams = layoutParams
        card.layoutParams = cardMarginLayoutParams
        binding.lyParameters.addView(card,0)
        searchBar.layoutParams = layoutParams

        card.addView(linearLayout)
        linearLayout.addView(searchBar)
        (binding.chipsFilter.parent as ViewGroup).removeView(binding.chipsFilter)

        chipHolderAnimationDuration = 0
        var animator : ValueAnimator

        searchBar.post {
            binding.lyParameters.requestLayout()

            card.radius = searchBar.height / 2f
            linearLayout.addView(binding.chipsFilter)
            linearLayout.addView(closeButton)

            closeButton.measure(MATCH_PARENT, WRAP_CONTENT)
            closeButton.layoutParams.height = closeButton.measuredHeight

            getBaseViewModel().filtersShown.observe(viewLifecycleOwner){ checked ->

                if(checked){
                    card.measure(MATCH_PARENT, WRAP_CONTENT);
                    val height = card.measuredHeight;
                    animator = ValueAnimator.ofInt(card.height, height)
                    animator.doOnEnd {
                        card.layoutParams.height = WRAP_CONTENT
                    }
                }
                else{
                    animator = ValueAnimator.ofInt(card.height, searchBar.height)
                }

                animator.cancel()
                animator.addUpdateListener {
                    val params = card.layoutParams
                    params.height = it.animatedValue as Int
                    card.layoutParams = params
                }
                animator.duration = chipHolderAnimationDuration
                chipHolderAnimationDuration = 250

                animator.start()
            }
        }

        if(binding.chipsFilter.childCount != 0) {


            searchBar.getEditText().setOnFocusChangeListener { focusedView, isFocused ->
                if (isFocused) {
                    getBaseViewModel().filtersShown.value = true
                }
            }
        }
        else{
            searchBar.getEditText().hint = getString(R.string.search)
        }

        closeButton.setOnClickListener{
            getBaseViewModel().filtersShown.value = false
            searchBar.getEditText().clearFocus()

            getBaseViewModel().filters = mutableMapOf()

            getBaseViewModel().dateFilters = mutableMapOf()

            for(chip in binding.chipsFilter.children){
                (chip as Chip).performCloseIconClick()
            }
        }

        if(getBaseViewModel().search != null){

            searchBar.getEditText().setText(getBaseViewModel().search!!.second)
        }

        searchBar.getEditText().doOnTextChanged { text, start, before, count ->
            if(searchBar.getEditText().text.isNullOrBlank()){
                getBaseViewModel().search = null
            }
            else{
                getBaseViewModel().search = Pair(filterName, ApplicationManager.searchApiWithRegex(searchBar.getEditText().text!!.toString()))
            }
        }


        searchBar.tag = searchBar.getEditText().text.toString()
        searchBar.getEditText().setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (searchBar.tag != searchBar.getEditText().text.toString()) {
                    getBaseViewModel().onChipsChanged?.invoke()
                }

                searchBar.tag = searchBar.getEditText().text.toString()
            }
            false
        }

        searchBar.getButton().setOnClickListener {
            searchBar.getEditText().setText("")
            if (searchBar.tag != searchBar.getEditText().text.toString()) {
                getBaseViewModel().onChipsChanged?.invoke()
            }


            searchBar.tag = ""
        }
    }

}