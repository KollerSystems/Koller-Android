package com.norbert.koller.shared.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
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
        onSetUpSearching()
    }

    open fun onSetUpSearching(){

    }

    fun setupSort(firstLocalizedString : Int, secondLocalizedString : Int, sortBy : String, firstSort : String = "asc", secondSort : String = "desc"){
        val firstChild = binding.chipsSort.getChildAt(0) as Chip
        firstChild.text = getString(firstLocalizedString)
        val secondChild = binding.chipsSort.getChildAt(1) as Chip
        secondChild.text = getString(secondLocalizedString)
        val chipToSelect = binding.chipsSort.getChildAt(viewModel.selectedSort) as Chip
        chipToSelect.isChecked = true
        binding.chipsSort.setOnCheckedStateChangeListener{group, list ->
            for (i in 0 until group.childCount){
                val chip = binding.chipsSort.getChildAt(i) as Chip
                if(chip.isChecked){
                    viewModel.selectedSort = i
                    viewModel.onChipsChanged?.invoke()
                    break
                }
            }

        }
        viewModel.sortOptions = arrayListOf(sortBy+":"+firstSort, sortBy+":"+secondSort)

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

    fun addSortingChip(filterName : String, localizedNameId : Int, getValues: suspend () -> Response<*>, classOfT: Class<*>, collapseText : Boolean = false){
        val chip = createChip()
        chip.connectToCheckBoxList(childFragmentManager, filterName, localizedNameId, getValues, viewModel, classOfT, collapseText)

    }
}