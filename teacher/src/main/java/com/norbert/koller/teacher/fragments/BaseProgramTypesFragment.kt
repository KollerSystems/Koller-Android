package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ProgramTypePagingSource
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.teacher.recycleradapters.BaseProgramTypeRecyclerAdapter

class BaseProgramTypesFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getFragmentTitle(): String? {
        return getString(R.string.base_programs)
    }

    override fun getPagingSource(): PagingSource {
        return ProgramTypePagingSource(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return BaseProgramTypeRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.abc, R.string.zyx,"Topic")


        addSearchbar("Topic")

    }

}