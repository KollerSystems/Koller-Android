package com.norbert.koller.shared.fragments

import android.content.Intent
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.RoomTidinessRecyclerAdapter

abstract class RoomTidinessListFragment() : ListFragment() {

    override fun getFragmentTitle(): String {
        return getString(R.string.room_tidinesses)
    }

    override fun getPagingSource(): PagingSource {
        return RoomPagingSource(requireContext(), getBaseViewModel())
    }


    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest, "Time", "desc", "asc")
    }
}