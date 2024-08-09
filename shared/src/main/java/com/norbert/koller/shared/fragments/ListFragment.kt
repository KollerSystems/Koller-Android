package com.norbert.koller.shared.fragments


import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.animation.doOnEnd
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.databinding.FragmentListBinding
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.helpers.connectToDateRangePicker
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response


abstract class ListFragment(var defaultFilters : MutableMap<String, ArrayList<String>>? = null) : FragmentInMainActivity() {

    lateinit var binding : FragmentListBinding

    lateinit var viewModel : BaseViewModel
    lateinit var apiRecyclerAdapter : ApiRecyclerAdapter
    var duration : Long = 0


    abstract fun getPagingSource() : PagingSource

    fun setFilter(filterIn : String, filterTo : String) : Fragment{
        defaultFilters = mutableMapOf(Pair(filterIn, arrayListOf(filterTo)))
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentListBinding.inflate(layoutInflater)
        val view = binding.root

        binding.scRecyclerView.getRecyclerView().layoutManager = LinearLayoutManager(context)
        binding.scRecyclerView.appBar = binding.appBar

        viewModel = ViewModelProvider(this)[BaseViewModel::class.java]

        if(defaultFilters != null) {
            viewModel.filters = defaultFilters!!
        }

        viewModel.pagingSource = {
            getPagingSource()
        }


        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.scRecyclerView.getRecyclerView().apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = apiRecyclerAdapter
        }



        lifecycleScope.launch {

            viewModel.pagingData.collectLatest { pagingData ->
                viewModel.currentPagingSource.recyclerAdapter = apiRecyclerAdapter
                apiRecyclerAdapter.submitData(pagingData)
            }
        }




        if(savedInstanceState != null) {
            if (apiRecyclerAdapter.snapshot().isEmpty()) {
                apiRecyclerAdapter.fullRefresh()
            }
        }
    }

    fun setupSort(firstLocalizedString : Int, secondLocalizedString : Int, sortBy : String, firstSort : String = "asc", secondSort : String = "desc"){
        val firstChild = binding.chipsSort.getChildAt(0) as Chip
        firstChild.tag = sortBy+":"+firstSort
        firstChild.text = getString(firstLocalizedString)
        val secondChild = binding.chipsSort.getChildAt(1) as Chip
        secondChild.tag = sortBy+":"+secondSort
        secondChild.text = getString(secondLocalizedString)
    }

    fun createChip() : Chip{
        val chip = Chip(requireContext())
        chip.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        binding.chipsFilter.addView(chip)
        chip.isCloseIconVisible = true
        return chip
    }

    fun addButton(text : String, onClick: () -> Unit) : Button {
        val button = MaterialButton(ContextThemeWrapper(requireContext() as MainActivity, R.style.ButtonTonalIcon))
        button.text = text
        button.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.add)
        val textColor = requireContext().getAttributeColor(com.google.android.material.R.attr.colorOnSecondaryContainer)
        button.setTextColor(textColor)
        button.setPaddingRelative(ApplicationManager.convertDpToPixel(16, requireContext()), button.paddingTop, button.paddingEnd, button.paddingBottom)
        button.iconTint = button.textColors
        val params = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        val textContainer = resources.getDimensionPixelSize(R.dimen.text_container_margin)
        params.setMargins(textContainer,textContainer,textContainer,textContainer)
        params.gravity = Gravity.CENTER
        button.layoutParams = params
        binding.lyParameters.addView(button)
        button.setOnClickListener {
            onClick.invoke()
        }
        return button
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


        closeButton.text = "Szűrők törlése"
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


        if(viewModel.filtersShown.value == false) {
            var anyOfHas = false
            for (chip in binding.chipsFilter.children) {
                chip as Chip
                if (chip.isChecked) {
                    anyOfHas = true
                }

                chip.measure(WRAP_CONTENT, WRAP_CONTENT)
                chip.layoutParams.height = chip.measuredHeight
            }
            viewModel.filtersShown.value = anyOfHas
        }




        linearLayout.layoutParams = layoutParams
        closeButton.layoutParams = layoutParams
        card.layoutParams = cardMarginLayoutParams
        binding.lyParameters.addView(card,0)
        searchBar.layoutParams = layoutParams


        card.addView(linearLayout)
        linearLayout.addView(searchBar)
        (binding.chipsFilter.parent as ViewGroup).removeView(binding.chipsFilter)



        duration = 0
        var animator : ValueAnimator


        searchBar.post {


            binding.lyParameters.requestLayout()

            card.radius = searchBar.height / 2f
            linearLayout.addView(binding.chipsFilter)
            linearLayout.addView(closeButton)

            closeButton.measure(MATCH_PARENT, WRAP_CONTENT)
            closeButton.layoutParams.height = closeButton.measuredHeight

            viewModel.filtersShown.observe(this){ checked ->


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
                animator.duration = duration
                duration = 250

                animator.start()
            }
        }


        if(binding.chipsFilter.childCount != 0) {


            searchBar.getEditText().setOnFocusChangeListener { focusedView, isFocused ->
                if (isFocused) {
                    viewModel.filtersShown.value = true
                }
            }
        }
        else{
            searchBar.getEditText().hint = getString(R.string.search)
        }

        closeButton.setOnClickListener{



            viewModel.filtersShown.value = false
            searchBar.getEditText().clearFocus()

            viewModel.filters.entries.removeIf { it.key != filterName }

            viewModel.dateFilters.entries.removeIf { it.key != filterName }

            for(chip in binding.chipsFilter.children){
                (chip as Chip).performCloseIconClick()
            }
        }

        if(viewModel.filters.containsKey(filterName)){

            searchBar.getEditText().setText(viewModel.filters[filterName]!![0])
        }

        searchBar.getEditText().doOnTextChanged { text, start, before, count ->
            if(searchBar.getEditText().text.isNullOrBlank()){
                viewModel.filters.remove(filterName)
            }
            else{

                viewModel.filters[filterName] = arrayListOf(ApplicationManager.searchApiWithRegex(searchBar.getEditText().text!!.toString()))
            }
        }




        apiRecyclerAdapter.searchBar = searchBar


    }

    fun addDateChip(filterName : String = "Date"){

        val chip = createChip()
        chip.connectToDateRangePicker(parentFragmentManager, filterName, viewModel)
    }

    fun addSortingChip(filterName : String, localizedNameId : Int, arrayList: ArrayList<ListItem>){

        val chip = createChip()
        chip.connectToCheckBoxList(childFragmentManager, filterName, localizedNameId, arrayList, viewModel)
    }

    fun addSortingChip(filterName : String, localizedNameId : Int, getValues: suspend () -> Response<*>, tag : String, collapseText : Boolean = false){

        val chip = createChip()
        chip.connectToCheckBoxList(childFragmentManager, filterName, localizedNameId, getValues, viewModel, tag, collapseText)
    }



}