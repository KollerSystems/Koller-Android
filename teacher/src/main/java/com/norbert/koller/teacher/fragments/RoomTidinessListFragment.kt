package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.RoomTidinessListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.teacher.activities.RoomRateActivity
import com.norbert.koller.teacher.recycleradapters.RoomTidinessRecyclerAdapter

class RoomTidinessListFragment : RoomTidinessListFragment() {
    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return RoomTidinessRecyclerAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButton("Szoba értékelése"){
            val intent = Intent(requireContext(), RoomRateActivity::class.java)
            startActivity(intent)
        }
    }
}