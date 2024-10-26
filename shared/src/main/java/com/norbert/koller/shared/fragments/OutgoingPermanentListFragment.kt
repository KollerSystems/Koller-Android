package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.OutgoingRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter

open class OutgoingPermanentListFragment() : ListFragment() {

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectUser()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return OutgoingRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest,"Time")
        addDateChip("Time")
        addSearchbar("Reason")

        if (arguments != null) {
            getBaseViewModel().ownerUID = requireArguments().getInt("id", -1)
        }
        if(getBaseViewModel().ownerUID == -1){
            getBaseViewModel().ownerUID = CacheManager.userData!!.uid
        }
    }
}