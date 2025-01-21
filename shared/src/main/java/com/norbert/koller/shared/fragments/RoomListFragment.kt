package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectGroup
import com.norbert.koller.shared.api.ApiDataObjectRoom
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.api.RetrofitInstance

import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.data.ListItem
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.recycleradapters.RoomRecyclerAdapter


open class RoomListFragment() : ListFragment() {

    override fun getFragmentTitle(): String? {
        return getString(R.string.places)
    }



    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectRoom()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return RoomRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.from_down, R.string.from_up,"RID")
        addSortingChip("Floor", R.string.floor_level, arrayListOf())
        addSortingChip("Annexe", R.string.annexe, arrayListOf())
        addSortingChip("Group.ID", R.string.group, ApiDataObjectGroup(), true)
        addSortingChip("Key", R.string.key, arrayListOf(
            ListToggleItem("Nem felvehető", null, AppCompatResources.getDrawable(requireContext(), R.drawable.key_off), 0),
            ListToggleItem("Felvett", null, AppCompatResources.getDrawable(requireContext(), R.drawable.key_active), 1),
            ListToggleItem("Leadott", null, AppCompatResources.getDrawable(requireContext(), R.drawable.key), 2)
        ))
        addSearchbar("RID")
    }
}