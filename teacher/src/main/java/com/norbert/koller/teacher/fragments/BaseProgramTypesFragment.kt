package com.norbert.koller.teacher.fragments

import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectBaseProgramType
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.teacher.recycleradapters.BaseProgramTypeRecyclerAdapter

class BaseProgramTypesFragment() : ListFragment() {

    override fun getFragmentTitle(): String {
        return getString(R.string.base_programs)
    }

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectBaseProgramType()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
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