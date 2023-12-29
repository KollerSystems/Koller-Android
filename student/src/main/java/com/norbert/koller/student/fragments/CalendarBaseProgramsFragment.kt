package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.viewmodels.BaseViewModel
import com.norbert.koller.shared.customview.SuperCoolRecyclerView
import com.norbert.koller.shared.api.BaseProgramPagingSource
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.fragments.FragmentList
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.helpers.connectToDateRangePicker
import com.norbert.koller.shared.recycleradapter.BasePagingSource
import com.norbert.koller.shared.recycleradapter.BaseProgramRecyclerAdapter
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.student.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.norbert.koller.shared.R as Rs


class CalendarBaseProgramsFragment : FragmentList() {

    override fun getPagingSource(): BasePagingSource {
        return BaseProgramPagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")
        baseRecycleAdapter = BaseProgramRecyclerAdapter(chipGroupSort, chipGroupFilter)

        addDateChip()

        val lessonLocalName : String = requireContext().getString(Rs.string.lesson).lowercase()

        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))

        addSearchbar()

        super.onViewCreated(view, savedInstanceState)
    }
}