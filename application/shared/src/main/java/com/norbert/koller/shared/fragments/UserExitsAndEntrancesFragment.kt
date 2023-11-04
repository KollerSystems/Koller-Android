package com.norbert.koller.shared.fragments

import android.content.Context
import androidx.core.util.Pair
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.CrossingData
import com.norbert.koller.shared.recycleradapter.GateRecyclerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.norbert.koller.shared.BaseViewModel
import com.norbert.koller.shared.SuperCoolRecyclerView
import com.norbert.koller.shared.api.CrossingPagingSource
import com.norbert.koller.shared.checkByPass
import com.norbert.koller.shared.data.FilterDateData
import com.norbert.koller.shared.data.FiltersData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.fragments.bottomsheet.RangeInputBshdFragment
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.recycleradapter.UserRecyclerAdapter
import com.norbert.koller.shared.restoreDropDown
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class UserExitsAndEntrancesFragment(val UID : Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_gate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipDirection : Chip = view.findViewById(R.id.chip_direction)
        val chipDate : Chip = view.findViewById(R.id.chip_date)
        val chipLateness : Chip = view.findViewById(R.id.chip_lateness)

        val chipGroupSort : ChipGroup = view.findViewById(R.id.chip_group_sort)


        MyApplication.setupCheckBoxList(childFragmentManager, chipDirection, "Direction", R.string.direction, arrayListOf(
            ListItem(getString(R.string.out), null, AppCompatResources.getDrawable(requireContext(), R.drawable.out), "0"),
            ListItem(getString(R.string.in_), null, AppCompatResources.getDrawable(requireContext(), R.drawable.in_), "1")
        ))

        MyApplication.setupDrpd(parentFragmentManager, chipDate)

        chipLateness.setOnClickListener {

            var fromTo : kotlin.Pair<Int?, Int?> = kotlin.Pair(null, null)
            if( chipLateness.tag != null && chipLateness.tag !is String){
                fromTo = chipLateness.tag as kotlin.Pair<Int?, Int?>
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

                    MyApplication.addCloseOptionToFilterChip(chipLateness, R.string.lateness)
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



        }




        val scRecyclerView : SuperCoolRecyclerView = view.findViewById(R.id.sc_recycler_view)
        scRecyclerView.appBar = view.findViewById(R.id.appbar_layout)

        val gateRecycleAdapter = GateRecyclerAdapter(chipGroupSort, listOf(chipDirection, chipDate))

        val viewModel = BaseViewModel {
            CrossingPagingSource(
                requireContext(),
                gateRecycleAdapter) }

        scRecyclerView.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gateRecycleAdapter
        }

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                gateRecycleAdapter.submitData(pagingData)
            }
        }
    }
}