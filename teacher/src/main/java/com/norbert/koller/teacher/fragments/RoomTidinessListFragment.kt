package com.norbert.koller.teacher.fragments

import android.content.Intent
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.RoomRecyclerAdapter
import com.norbert.koller.teacher.activities.RoomRateActivity

class RoomTidinessListFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getFragmentTitle(): String {
        return getString(R.string.room_order)
    }

    override fun getPagingSource(): PagingSource {
        return RoomPagingSource(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return RoomRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest, "Time", "desc", "asc")

        addButton("Szoba értékelése"){
            val intent = Intent(requireContext(), RoomRateActivity::class.java)
            startActivity(intent)
        }
    }
}