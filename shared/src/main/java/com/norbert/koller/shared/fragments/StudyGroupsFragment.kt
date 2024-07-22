package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.StudyGroupTypePagingSource
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class StudyGroupsFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getPagingSource(): PagingSource {
        return StudyGroupTypePagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.abc, R.string.zyx,"Topic")

        apiRecyclerAdapter.chipGroupSort = chipGroupSort
        apiRecyclerAdapter.chipGroupFilter = chipGroupFilter

        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }

}