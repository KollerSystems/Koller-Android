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
import com.norbert.koller.shared.data.FilterDateData
import com.norbert.koller.shared.data.FiltersData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.fragments.bottomsheet.RangeInputBshdFragment
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.recycleradapter.UserRecyclerAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class UserExitsAndEntrancesFragment(val UID : Int) : Fragment() {

    private lateinit var scRecyclerView: SuperCoolRecyclerView

    var filterOut : Boolean = false
    var filterIn : Boolean = false
    var filterDirections : ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_user_gate, container, false)

        val chipGroupSort : ChipGroup = view.findViewById(R.id.chip_group_sort)

        val chipDirection : Chip = view.findViewById(R.id.chip_direction)
        val chipDate : Chip = view.findViewById(R.id.chip_date)

        val chipLateness : Chip = view.findViewById(com.norbert.koller.shared.R.id.chip_lateness)
        chipLateness.setOnClickListener {
            val dialog = RangeInputBshdFragment()
            dialog.show(childFragmentManager, RangeInputBshdFragment.TAG)
        }

        MyApplication.setupDrpd(requireContext(), parentFragmentManager, chipDate)


        chipDirection.setOnClickListener {
            val dialog = ItemListDialogFragment()
            dialog.show(childFragmentManager, ItemListDialogFragment.TAG)

            dialog.list = arrayListOf(
                ListItem({isChecked ->
                    filterOut = isChecked
                }, getString(R.string.out), null, AppCompatResources.getDrawable(requireContext(), R.drawable.out), filterOut, "0"),
                ListItem({isChecked ->
                    filterIn = isChecked
                }, getString(R.string.in_), null, AppCompatResources.getDrawable(requireContext(), R.drawable.in_), filterIn, "1")
            )

            dialog.getValuesOnFinish = {values, locNames ->

                filterDirections = values
                MyApplication.editChipBasedOnResponse(requireContext(), chipDirection, values, locNames, R.string.direction)

            }
        }


        val crossingRecycleAdapter = GateRecyclerAdapter(chipGroupSort, listOf(chipDirection, chipDate))

        val viewModel = BaseViewModel { CrossingPagingSource(requireContext(), crossingRecycleAdapter,
            MyApplication.getApiSortString(chipGroupSort),
            MyApplication.createApiFilter(
                arrayOf(FiltersData("Direction", filterDirections)),
                arrayOf(FilterDateData("Date", (chipDate.tag as Pair<Long, Long>?))))) }

        scRecyclerView = view.findViewById(R.id.sc_recycler_view)

        scRecyclerView.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = crossingRecycleAdapter
        }


        scRecyclerView.appBar = view.findViewById(R.id.appbar_layout)

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                crossingRecycleAdapter.submitData(pagingData)
            }
        }

        return view
    }
}