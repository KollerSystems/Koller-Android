package com.norbert.koller.shared.fragments

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.customviews.SuperCoolRecyclerView
import com.norbert.koller.shared.helpers.arrayToString
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.helpers.connectToDateRangePicker
import com.norbert.koller.shared.helpers.resetSimpleChip
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.setVisibilityBy
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
    lateinit var lyFilters : LinearLayout
    lateinit var lyParameters : LinearLayout


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
        lyFilters = view.findViewById(R.id.ly_filters)
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

    fun addSearchbar(filterName : String){
        checkIfFiltersShouldBeShowed(false)

        val searchBar = SearchView(requireContext())
        val marginLayoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        marginLayoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.text_container_margin)
        marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.spacing)
        searchBar.layoutParams = marginLayoutParams
        lyParameters.addView(searchBar,0)

        searchBar.editTextSearch.setOnFocusChangeListener{focusedView, isFocused ->
            checkIfFiltersShouldBeShowed(isFocused)
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

        for (chip in chipGroupFilter.children){
            chip as Chip
            chip.doOnTextChanged{ _, _, _, _ ->
                checkIfFiltersShouldBeShowed(searchBar.isFocused)
                searchBar.clearFocus()
            }
        }

        lyParameters.layoutTransition = LayoutTransition()

        viewModel.filters

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

    fun addSortingChip(filterName : String, localizedNameId : Int, getValues: suspend () -> Response<*>, tag : String){

        val chip = createChip()
        chip.connectToCheckBoxList(childFragmentManager, filterName, localizedNameId, getValues, viewModel, tag)
    }

    fun checkIfFiltersShouldBeShowed(isFocused : Boolean){
        if(isFocused){
            lyFilters.setVisibilityBy(true)
        }
        else{
            var anyOfHas = false
            for (chip in chipGroupFilter.children){
                chip as Chip
                if(chip.isChecked){
                    anyOfHas = true
                }
            }
            lyFilters.setVisibilityBy(anyOfHas)
        }
    }



}