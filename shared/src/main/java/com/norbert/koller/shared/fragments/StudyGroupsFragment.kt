package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.StudyGroupPagingSource
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class StudyGroupsFragment : ListFragment() {

    override fun getFragmentTitle(): String? {
        return getString(R.string.study_groups)
    }


    override fun getPagingSource(): PagingSource {
        return StudyGroupPagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")
        apiRecyclerAdapter.chipsSort = binding.chipsSort
        apiRecyclerAdapter.chipsFilter = binding.chipsFilter

        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }
}