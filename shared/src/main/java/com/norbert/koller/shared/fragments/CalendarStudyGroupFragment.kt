package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.api.StudyGroupPagingSource
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class CalendarStudyGroupFragment : ListFragment() {



    override fun getPagingSource(): PagingSource {
        return StudyGroupPagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")
        apiRecyclerAdapter.chipGroupSort = chipGroupSort
        apiRecyclerAdapter.chipGroupFilter = chipGroupFilter

        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }
}