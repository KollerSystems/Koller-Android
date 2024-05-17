package com.norbert.koller.shared.fragments


import android.animation.Animator

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewGroup.VISIBLE
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
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.customviews.SuperCoolRecyclerView
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.helpers.connectToDateRangePicker
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.BaseRecycleAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response


abstract class ListFragment(var defaultFilters : MutableMap<String, ArrayList<String>>? = null) : Fragment() {

    lateinit var viewModel : BaseViewModel
    lateinit var superCoolRecyclerView: SuperCoolRecyclerView
    lateinit var baseRecycleAdapter : BaseRecycleAdapter
    lateinit var chipGroupFilter : ChipGroup
    lateinit var chipGroupSort : ChipGroup
    lateinit var lyParameters : FlexboxLayout
    var duration : Long = 0


    abstract fun getPagingSource() : BasePagingSource

    fun setFilter(filterIn : String, filterTo : String) : Fragment{
        defaultFilters = mutableMapOf(Pair(filterIn, arrayListOf(filterTo)))
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        chipGroupFilter = view.findViewById(R.id.chip_group_filter)
        chipGroupSort = view.findViewById(R.id.chip_group_sort)


        superCoolRecyclerView = view.findViewById(R.id.super_cool_recycler_view)
        lyParameters = view.findViewById(R.id.ly_parameters)

        superCoolRecyclerView.recyclerView.layoutManager = LinearLayoutManager(context)
        superCoolRecyclerView.appBar = view.findViewById(R.id.appbar_layout)

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


        superCoolRecyclerView.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = baseRecycleAdapter
        }



        lifecycleScope.launch {

            viewModel.pagingData.collectLatest { pagingData ->
                viewModel.currentPagingSource.recyclerAdapter = baseRecycleAdapter
                baseRecycleAdapter.submitData(pagingData)
            }
        }




        if(savedInstanceState != null) {
            if (baseRecycleAdapter.snapshot().isEmpty()) {
                baseRecycleAdapter.fullRefresh()
            }
        }
    }

    fun setupSort(firstLocalizedString : Int, secondLocalizedString : Int, sortBy : String, firstSort : String = "asc", secondSort : String = "desc"){
        val firstChild = chipGroupSort.getChildAt(0) as Chip
        firstChild.tag = sortBy+":"+firstSort
        firstChild.text = getString(firstLocalizedString)
        val secondChild = chipGroupSort.getChildAt(1) as Chip
        secondChild.tag = sortBy+":"+secondSort
        secondChild.text = getString(secondLocalizedString)
    }

    fun createChip() : Chip{
        val chip = Chip(requireContext())
        chip.layoutParams = ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        chipGroupFilter.addView(chip)
        chip.isCloseIconVisible = true
        return chip
    }

    fun addButton(text : String, onClick: () -> Unit) {
        val button = MaterialButton(ContextThemeWrapper(requireContext() as MainActivity, R.style.ButtonTonalIcon))
        button.text = text
        button.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.add)
        val textColor = requireContext().getAttributeColor(com.google.android.material.R.attr.colorOnSecondaryContainer)
        button.setTextColor(textColor)
        button.iconTint = button.textColors
        val params = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        val textContainer = resources.getDimensionPixelSize(R.dimen.text_container_margin)
        params.setMargins(textContainer,textContainer,textContainer,textContainer)
        params.gravity = Gravity.CENTER
        button.layoutParams = params
        lyParameters.addView(button)
        button.setOnClickListener {
            onClick.invoke()
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

        val textColor = searchBar.editTextSearch.textColors.defaultColor

        val textViewFilters : TextView = requireView().findViewById(R.id.textView_filters)
        (textViewFilters.parent as ViewGroup).removeView(textViewFilters)


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
        cardMarginLayoutParams.maxWidth = (chipGroupFilter.layoutParams as FlexboxLayout.LayoutParams).maxWidth
        val chipMarginLayoutParams = FlexboxLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val margin = resources.getDimensionPixelSize(R.dimen.card_padding)
        chipMarginLayoutParams.setMargins(margin,margin,margin,margin)
        chipGroupFilter.layoutParams = chipMarginLayoutParams


        if(viewModel.filtersShown.value == false) {
            var anyOfHas = false
            for (chip in chipGroupFilter.children) {
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
        lyParameters.addView(card,0)
        searchBar.layoutParams = layoutParams


        card.addView(linearLayout)
        linearLayout.addView(searchBar)
        (chipGroupFilter.parent as ViewGroup).removeView(chipGroupFilter)



        duration = 0
        var animator : ValueAnimator


        searchBar.post {


            lyParameters.requestLayout()

            card.radius = searchBar.height / 2f
            linearLayout.addView(chipGroupFilter)
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


        searchBar.editTextSearch.setOnFocusChangeListener{focusedView, isFocused ->
            if(isFocused){
                viewModel.filtersShown.value = true
            }
        }

        closeButton.setOnClickListener{



            viewModel.filtersShown.value = false
            searchBar.editTextSearch.clearFocus()

            viewModel.filters.entries.removeIf { it.key != filterName }

            viewModel.dateFilters.entries.removeIf { it.key != filterName }

            for(chip in chipGroupFilter.children){
                (chip as Chip).performCloseIconClick()
            }
        }

        if(viewModel.filters.containsKey(filterName)){

            searchBar.editTextSearch.setText(viewModel.filters[filterName]!![0])
        }

        searchBar.editTextSearch.doOnTextChanged { text, start, before, count ->
            if(searchBar.editTextSearch.text.isNullOrBlank()){
                viewModel.filters.remove(filterName)
            }
            else{

                viewModel.filters[filterName] = arrayListOf(ApplicationManager.searchApiWithRegex(searchBar.editTextSearch.text!!.toString()))
            }
        }




        baseRecycleAdapter.searchBar = searchBar


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