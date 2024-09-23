package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.RoomRecyclerAdapter


open class RoomListFragment() : ListFragment() {

    override fun getFragmentTitle(): String? {
        return getString(R.string.places)
    }

    override fun getPagingSource(): PagingSource {
        return RoomPagingSource(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return RoomRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.from_down, R.string.from_up,"RID")
        addSortingChip("Floor", R.string.floor_level, arrayListOf())
        addSortingChip("Annexe", R.string.annexe, arrayListOf())
        addSortingChip("Group.ID", R.string.group, {RetrofitInstance.api.getGroups()}, GroupData::class.java, true)
        addSearchbar("RID")
    }
}