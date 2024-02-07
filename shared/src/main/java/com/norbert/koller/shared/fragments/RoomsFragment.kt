package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.RoomRecyclerAdapter


open class RoomsFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getPagingSource(): BasePagingSource {
        return RoomPagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.from_down, R.string.from_up,"RID")

        baseRecycleAdapter = RoomRecyclerAdapter()
        baseRecycleAdapter.chipGroupSort = chipGroupSort
        baseRecycleAdapter.chipGroupFilter = chipGroupFilter

        addSortingChip("Floor", R.string.floor_level, arrayListOf(
        ))


        addSortingChip("Annexe", R.string.annexe, arrayListOf(
        ))

        addSortingChip("Group.ID", R.string.group, {RetrofitInstance.api.getGroups()}, "group")


        addSearchbar("RID")


        super.onViewCreated(view, savedInstanceState)
    }
}