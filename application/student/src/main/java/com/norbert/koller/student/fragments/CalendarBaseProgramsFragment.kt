package com.norbert.koller.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.BaseViewModel
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.SuperCoolRecyclerView
import com.norbert.koller.shared.api.BaseProgramPagingSource
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.recycleradapter.BaseProgramRecyclerAdapter
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.recycleradapter.UserRecyclerAdapter
import com.norbert.koller.student.R
import com.norbert.koller.shared.R as Rs
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CalendarBaseProgramsFragment : Fragment() {


    var lengthFilters : ArrayList<String> = arrayListOf()

    var lengthOption : ArrayList<Boolean> = arrayListOf(false, false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_calendar_base_programs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scRecyclerView : SuperCoolRecyclerView = view.findViewById(R.id.sc_recycler_view)

        val chipLength : Chip = view.findViewById(R.id.chip_length)

        val chipGroupSort : ChipGroup = view.findViewById(Rs.id.chip_group_sort)

        val baseProgramRecycleAdapter = BaseProgramRecyclerAdapter(chipGroupSort, listOf(chipLength))


        chipLength.setOnClickListener {
            val dialog = ItemListDialogFragment()
            dialog.show(childFragmentManager, ItemListDialogFragment.TAG)

            val lessonLocalName : String = requireContext().getString(Rs.string.lesson)

            dialog.list = arrayListOf(
                ListItem(
                    { isChecked ->
                        lengthOption[0] = isChecked
                    },
                    "1 ${lessonLocalName}", null, null, lengthOption[0], "1"),
                ListItem(
                    { isChecked ->
                        lengthOption[1] = isChecked
                    },
                    "2 ${lessonLocalName}", null, null, lengthOption[1], "2")
            )

            dialog.getValuesOnFinish = {values, locNames ->

                lengthFilters = values

                MyApplication.editChipBasedOnResponse(requireContext(),chipLength,values,locNames, com.norbert.koller.shared.R.string.length)

            }
        }



        val viewModel = BaseViewModel { BaseProgramPagingSource(requireContext(), BaseProgramRecyclerAdapter(), MyApplication.getApiSortString(chipGroupSort),  MyApplication.createApiFilter("Length", lengthFilters)) }

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