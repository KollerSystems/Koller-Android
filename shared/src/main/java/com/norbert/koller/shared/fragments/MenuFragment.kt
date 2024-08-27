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
import com.norbert.koller.shared.helpers.connectToDateRangePickerWithTemplates
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.BaseViewModel
import com.norbert.koller.shared.viewmodels.MenuViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response


abstract class MenuFragment() : FragmentInMainActivity() {

    lateinit var binding : FragmentListBinding

    lateinit var viewModel : MenuViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentListBinding.inflate(layoutInflater)
        val view = binding.root

        assignViewModel()


        return view
    }


    open fun assignViewModel(){
        viewModel = ViewModelProvider(this)[BaseViewModel::class.java]
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