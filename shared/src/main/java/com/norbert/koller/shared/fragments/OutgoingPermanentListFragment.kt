package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter

open class OutgoingPermanentListFragment(val userData: UserData? = null) : ListFragment() {

    override fun getPagingSource(): PagingSource {
        return UserPagingSource(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return UserRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest,"Time")
        addDateChip("Time")
        addSearchbar("Reason")

        if(userData != null)
            getBaseViewModel().owner = userData
    }
}