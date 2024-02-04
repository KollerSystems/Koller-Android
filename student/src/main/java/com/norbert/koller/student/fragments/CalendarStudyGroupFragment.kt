package com.norbert.koller.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.api.BaseProgramPagingSource
import com.norbert.koller.shared.api.StudyGroupPagingSource
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.BaseProgramRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.StudyGroupRecyclerAdapter
import com.norbert.koller.student.R

class CalendarStudyGroupFragment : ListFragment() {

    override fun getPagingSource(): BasePagingSource {
        return StudyGroupPagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")
        baseRecycleAdapter = StudyGroupRecyclerAdapter()
        baseRecycleAdapter.chipGroupSort = chipGroupSort
        baseRecycleAdapter.chipGroupFilter = chipGroupFilter

        addDateChip()

        val lessonLocalName : String = requireContext().getString(com.norbert.koller.shared.R.string.lesson).lowercase()

        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))

        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }
}