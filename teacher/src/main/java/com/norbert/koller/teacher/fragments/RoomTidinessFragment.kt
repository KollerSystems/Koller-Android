package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ProgramTypePagingSource
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.GateRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.RoomRecyclerAdapter
import com.norbert.koller.teacher.activities.RoomRateActivity
import com.norbert.koller.teacher.recycleradapters.BaseProgramTypeRecyclerAdapter

class RoomTidinessFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getFragmentTitle(): String? {
        return getString(R.string.room_order)
    }

    override fun getPagingSource(): PagingSource {
        return RoomPagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.newest, R.string.oldest, "Time", "desc", "asc")
        apiRecyclerAdapter = RoomRecyclerAdapter()
        apiRecyclerAdapter.chipsSort = binding.chipsSort
        apiRecyclerAdapter.chipsFilter = binding.chipsFilter

        addButton("Szoba értékelése"){
            val intent = Intent(requireContext(), RoomRateActivity::class.java)
            startActivity(intent)
        }

        super.onViewCreated(view, savedInstanceState)
    }
}