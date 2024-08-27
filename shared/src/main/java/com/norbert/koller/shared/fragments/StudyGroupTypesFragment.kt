package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.StudyGroupTypePagingSource
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class StudyGroupTypesFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getFragmentTitle(): String? {
        return getString(R.string.study_groups)
    }

    override fun getPagingSource(): PagingSource {
        return StudyGroupTypePagingSource(requireContext(), getBaseViewModel())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.abc, R.string.zyx,"Topic")

        apiRecyclerAdapter.chipsSort = binding.chipsSort
        apiRecyclerAdapter.chipsFilter = binding.chipsFilter

        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }

}