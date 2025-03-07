package com.norbert.koller.teacher.fragments

import android.content.Intent
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObject
import com.norbert.koller.shared.api.ApiDataObjectRoom
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.teacher.activities.RoomPresenceActivity
import com.norbert.koller.teacher.activities.RoomRateActivity
import com.norbert.koller.teacher.recycleradapters.RoomPresenceRecyclerAdapter

class RoomPresenceListFragment() : ListFragment() {

    override fun getFragmentTitle(): String {
        return getString(R.string.room_order)
    }

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectRoom()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return RoomPresenceRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest, "Time", "desc", "asc")

        addButton("Jelenlét hozzáadása"){
            val intent = Intent(requireContext(), RoomPresenceActivity::class.java)
            startActivity(intent)
        }
    }
}