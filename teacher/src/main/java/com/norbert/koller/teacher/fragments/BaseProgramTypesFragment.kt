package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ProgramTypePagingSource
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.teacher.recycleradapters.BaseProgramTypeRecyclerAdapter

class BaseProgramTypesFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getFragmentTitle(): String? {
        return getString(R.string.base_programs)
    }

    override fun getPagingSource(): PagingSource {
        return ProgramTypePagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.abc, R.string.zyx,"Topic")
        apiRecyclerAdapter = BaseProgramTypeRecyclerAdapter()
        apiRecyclerAdapter.chipsSort = binding.chipsSort
        apiRecyclerAdapter.chipsFilter = binding.chipsFilter


        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }

}