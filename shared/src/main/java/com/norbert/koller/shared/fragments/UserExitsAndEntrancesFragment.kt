package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R
import com.norbert.koller.shared.recycleradapters.GateRecyclerAdapter
import com.norbert.koller.shared.api.CrossingPagingSource
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.ListItem

class UserExitsAndEntrancesFragment(val uid : Int? = null) : ListFragment() {

    override fun getPagingSource(): BasePagingSource {
        return CrossingPagingSource(requireContext(), viewModel.id, viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.newest, R.string.oldest, "Time", "desc", "asc")
        baseRecycleAdapter = GateRecyclerAdapter(chipGroupSort, chipGroupFilter)

        if(uid != null) {
            viewModel.id = uid
        }

        addSortingChip("Direction", R.string.direction, arrayListOf(
            ListItem(getString(R.string.out), null, AppCompatResources.getDrawable(requireContext(), R.drawable.out), "1"),
            ListItem(getString(R.string.in_), null, AppCompatResources.getDrawable(requireContext(), R.drawable.in_), "0")
        ))

        addDateChip("Time")



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

        super.onViewCreated(view, savedInstanceState)
    }
}