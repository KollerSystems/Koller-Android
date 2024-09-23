package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R
import com.norbert.koller.shared.recycleradapters.GateRecyclerAdapter
import com.norbert.koller.shared.api.CrossingPagingSource
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.ListItem

class CrossingListFragment() : ListFragment() {


    override fun getFragmentTitleAndDescription(): Pair<String?, String?> {
        return if(getBaseViewModel().ownerUID == CacheManager.userData!!.uid){
            Pair(getString(R.string.port_exits_and_entrances),"")
        } else{
            Pair(
                (CacheManager.detailsDataMap[Pair(UserData::class.java.simpleName, getBaseViewModel().ownerUID)] as UserData).name,
                getString(R.string.port_exits_and_entrances)
            )
        }
    }

    override fun getPagingSource(): PagingSource {
        return CrossingPagingSource(requireContext(), getBaseViewModel().ownerUID, getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return GateRecyclerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        if (arguments != null) {
            getBaseViewModel().ownerUID = requireArguments().getInt("id", -1)
        }
        if(getBaseViewModel().ownerUID == -1){
            getBaseViewModel().ownerUID = CacheManager.userData!!.uid
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest, "Time", "desc", "asc")
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
    }
}