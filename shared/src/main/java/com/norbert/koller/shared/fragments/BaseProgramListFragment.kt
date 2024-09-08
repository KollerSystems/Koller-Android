package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.api.ProgramPagingSource
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class BaseProgramListFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getFragmentTitle(): String? {
        return null
    }

    override fun getPagingSource(): PagingSource {
        return ProgramPagingSource(requireContext(), getBaseViewModel())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")

        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }
}