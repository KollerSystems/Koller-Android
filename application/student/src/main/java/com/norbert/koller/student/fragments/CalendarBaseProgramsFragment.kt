package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.recycleradapter.BaseViewModel
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.customview.SuperCoolRecyclerView
import com.norbert.koller.shared.api.BaseProgramPagingSource
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.helpers.connectToDateRangePicker
import com.norbert.koller.shared.recycleradapter.BaseProgramRecyclerAdapter
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.student.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.norbert.koller.shared.R as Rs


class CalendarBaseProgramsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_calendar_base_programs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scRecyclerView : SuperCoolRecyclerView = view.findViewById(R.id.sc_recycler_view)

        val chipLength : Chip = view.findViewById(R.id.chip_length)

        val chipGroupSort : ChipGroup = view.findViewById(Rs.id.chip_group_sort)

        val chipDate : Chip = view.findViewById(R.id.chip_date)



        chipDate.connectToDateRangePicker(parentFragmentManager)


        val baseProgramRecycleAdapter = BaseProgramRecyclerAdapter(chipGroupSort, listOf(chipLength, chipDate))

        val lessonLocalName : String = requireContext().getString(Rs.string.lesson).lowercase()

        chipLength.connectToCheckBoxList(childFragmentManager, "Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))


        val viewModel = BaseViewModel { BaseProgramPagingSource(requireContext(), baseProgramRecycleAdapter) }

        scRecyclerView.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = baseProgramRecycleAdapter
        }


        scRecyclerView.appBar = view.findViewById(com.norbert.koller.shared.R.id.appbar_layout)

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                baseProgramRecycleAdapter.submitData(pagingData)
            }
        }
    }
}