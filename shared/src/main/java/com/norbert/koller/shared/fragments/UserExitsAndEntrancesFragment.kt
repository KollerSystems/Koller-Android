package com.norbert.koller.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.recycleradapter.GateRecyclerAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.viewmodels.BaseViewModel
import com.norbert.koller.shared.customview.SuperCoolRecyclerView
import com.norbert.koller.shared.api.CrossingPagingSource
import com.norbert.koller.shared.checkByPass
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.fragments.bottomsheet.RangeInputBshdFragment
import com.norbert.koller.shared.helpers.addCloseOption
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.helpers.connectToDateRangePicker
import com.norbert.koller.shared.recycleradapter.BasePagingSource
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.restoreDropDown
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserExitsAndEntrancesFragment(val UID : Int? = null) : FragmentList() {

    companion object{
        var savedValues : ArrayList<BaseData>? = null
    }

    override fun onSavedList(list: ArrayList<BaseData>?) {
        savedValues = list
    }

    override fun getSavedList(): ArrayList<BaseData>? {
        return savedValues
    }

    override fun getPagingSource(): BasePagingSource {
        return CrossingPagingSource(requireContext(), viewModel.ID, baseRecycleAdapter, viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(UID != null) {
            viewModel.ID = UID
        }

        baseRecycleAdapter = GateRecyclerAdapter(chipGroupSort, chipGroupFilter)

        addSortingChip("Direction", R.string.direction, arrayListOf(
            ListItem(getString(R.string.out), null, AppCompatResources.getDrawable(requireContext(), R.drawable.out), "1"),
            ListItem(getString(R.string.in_), null, AppCompatResources.getDrawable(requireContext(), R.drawable.in_), "0")
        ))

        addDateChip("Time")

        setupSort(R.string.newest, R.string.oldest, "Time", "desc", "asc")

        /*chipLateness.setOnClickListener {

            var fromTo : Pair<Int?, Int?> = Pair(null, null)
            if( chipLateness.tag != null && chipLateness.tag !is String){
                fromTo = chipLateness.tag as Pair<Int?, Int?>
            }
            val dialog = RangeInputBshdFragment(fromTo)
            dialog.show(childFragmentManager, RangeInputBshdFragment.TAG)

            dialog.getValuesOnFinish = {values ->

                val hasValue = values.first != null
                var stringForChip : String
                if(hasValue) {
                    stringForChip = "${values.first}p"
                    if (values.first != values.second) {
                        stringForChip += " - ${values.second}p"
                    }
                    chipLateness.tag = values

                    chipLateness.addCloseOption(R.string.lateness)
                }
                else{
                    stringForChip = getString(R.string.lateness)
                    chipLateness.tag = null
                    chipLateness.restoreDropDown()
                }

                if (chipLateness.text.toString() != stringForChip) {
                    chipLateness.text = stringForChip
                }
                chipLateness.checkByPass(hasValue)
            }



        }*/


    }
}