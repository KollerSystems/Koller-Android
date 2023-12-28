package com.norbert.koller.shared.fragments

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import androidx.core.view.setMargins
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.customview.SearchView
import com.norbert.koller.shared.customview.SuperCoolRecyclerView
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.FilterBaseData
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.helpers.connectToDateRangePicker
import com.norbert.koller.shared.recycleradapter.BasePagingSource
import com.norbert.koller.shared.recycleradapter.BaseRecycleAdapter
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.recycleradapter.UserRecyclerAdapter
import com.norbert.koller.shared.setVisibilityBy
import com.norbert.koller.shared.viewmodels.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class FragmentList(val defaultFilters : MutableMap<String, ArrayList<String>>? = null) : Fragment() {

    lateinit var viewModel : BaseViewModel
    lateinit var superCoolRecyclerView: SuperCoolRecyclerView
    lateinit var baseRecycleAdapter : BaseRecycleAdapter
    lateinit var chipGroupFilter : ChipGroup
    lateinit var chipGroupSort : ChipGroup
    lateinit var lyFilters : LinearLayout
    lateinit var lyParameters : LinearLayout


    abstract fun onSavedList(list : ArrayList<BaseData>?)
    abstract fun getSavedList() : ArrayList<BaseData>?
    abstract fun getPagingSource() : BasePagingSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
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

    fun addSearchbar(){
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

        for (chip in chipGroupFilter.children){
            chip as Chip
            chip.doOnTextChanged{ _, _, _, _ ->
                checkIfFiltersShouldBeShowed(searchBar.isFocused)
                searchBar.clearFocus()
            }
        }

        lyParameters.layoutTransition = LayoutTransition()
    }

    fun addDateChip(filterName : String = "Date"){

        val chip = createChip()
        chip.connectToDateRangePicker(parentFragmentManager, filterName, viewModel)
    }

    fun addSortingChip(filterName : String, localizedNameId : Int, arrayList: ArrayList<ListItem>){

        val chip = createChip()
        chip.connectToCheckBoxList(childFragmentManager, filterName, localizedNameId, arrayList, viewModel)
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        superCoolRecyclerView = view.findViewById(R.id.super_cool_recycler_view)
        chipGroupFilter = view.findViewById(R.id.chip_group_filter)
        chipGroupSort = view.findViewById(R.id.chip_group_sort)
        lyFilters = view.findViewById(R.id.ly_filters)
        lyParameters = view.findViewById(R.id.ly_parameters)

        superCoolRecyclerView.recyclerView.layoutManager = LinearLayoutManager(context)
        superCoolRecyclerView.appBar = view.findViewById(R.id.appbar_layout)

        viewModel = ViewModelProvider(this)[BaseViewModel::class.java]

        if(defaultFilters != null) {
            viewModel.filters = defaultFilters
        }




        superCoolRecyclerView.post {

            viewModel.pagingSource = {
                val pagingSource = getPagingSource()
                if (viewModel.filters.isEmpty() && viewModel.dateFilters.isEmpty() && chipGroupSort.checkedChipId == R.id.first) {
                    pagingSource.savedValues = getSavedList()
                }
                pagingSource
            }

            superCoolRecyclerView.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = baseRecycleAdapter
            }

            lifecycleScope.launch {

                viewModel.pagingData.collectLatest { pagingData ->
                    baseRecycleAdapter.submitData(pagingData)
                }
            }

            if(savedInstanceState != null) {
                if (baseRecycleAdapter.snapshot().isEmpty()) {
                    baseRecycleAdapter.refreshFully()
                }
            }
        }
    }


    override fun onStop() {
        super.onStop()
        val rcItems = baseRecycleAdapter.snapshot().items

        if(rcItems.isEmpty() || (viewModel.filters.isNotEmpty() || viewModel.dateFilters.isNotEmpty() || chipGroupSort.checkedChipId == R.id.second)){
            return
        }

        val list : ArrayList<BaseData> = arrayListOf()
        for (rcItem in rcItems){
            if(rcItem is BaseData){
                list.add(rcItem)
            }
        }
        onSavedList(list)
    }
}