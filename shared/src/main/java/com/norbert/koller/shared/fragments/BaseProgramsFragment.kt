package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.api.ProgramPagingSource
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class BaseProgramsFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getPagingSource(): PagingSource {
        return ProgramPagingSource(requireContext(), viewModel)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")

        apiRecyclerAdapter.chipGroupSort = binding.chipsSort
        apiRecyclerAdapter.chipGroupFilter = binding.chipsFilter

        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }
}