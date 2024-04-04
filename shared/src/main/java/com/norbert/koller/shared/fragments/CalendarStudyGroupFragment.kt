package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.api.StudyGroupPagingSource
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.StudyGroupRecyclerAdapter

abstract class CalendarStudyGroupFragment : ListFragment() {



    override fun getPagingSource(): BasePagingSource {
        return StudyGroupPagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")
        baseRecycleAdapter.chipGroupSort = chipGroupSort
        baseRecycleAdapter.chipGroupFilter = chipGroupFilter

        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }
}