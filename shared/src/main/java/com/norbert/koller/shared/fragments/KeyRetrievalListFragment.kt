package com.norbert.koller.shared.fragments

import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.OutgoingRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator

class KeyRetrievalListFragment : ListFragment() {
    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectUser()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return OutgoingRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest, "Time")
        addDateChip("Time")
        addSearchbar("Reason")
        addButton(getString(R.string.add_key)){

        }

        if (arguments != null) {
            getBaseViewModel().ownerUID = requireArguments().getInt("id", -1)
        }
        if (getBaseViewModel().ownerUID == -1) {
            getBaseViewModel().ownerUID = CacheManager.getCurrentUserData()!!.uid
        }
    }
}