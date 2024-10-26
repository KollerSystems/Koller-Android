package com.norbert.koller.shared.fragments

import android.content.Intent
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectRoom
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.recycleradapters.RoomTidinessRecyclerAdapter

abstract class RoomTidinessListFragment() : ListFragment() {

    override fun getFragmentTitle(): String {
        return getString(R.string.room_tidinesses)
    }

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectRoom()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }


    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest, "Time", "desc", "asc")
    }
}