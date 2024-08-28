package com.norbert.koller.shared.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.databinding.FragmentSearchBinding
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.helpers.connectToDateRangePicker
import com.norbert.koller.shared.helpers.connectToDateRangePickerWithTemplates
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.ListViewModel
import com.norbert.koller.shared.viewmodels.SearchViewModel
import retrofit2.Response


abstract class SearchFragment() : FragmentInMainActivity() {

    lateinit var binding : FragmentSearchBinding

    lateinit var viewModel : SearchViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)
        val view = binding.root

        assignViewModel()


        return view
    }


    open fun assignViewModel(){
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    fun addDateChip(filterName : String = "Date"){

        val chip = createChip()
        chip.connectToDateRangePicker(parentFragmentManager, filterName, viewModel)
    }

    fun addDateChipWithTemplates(filterName : String = "Date"){

        val chip = createChip()
        chip.connectToDateRangePickerWithTemplates(parentFragmentManager, filterName, viewModel)
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