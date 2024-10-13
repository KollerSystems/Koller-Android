package com.norbert.koller.student.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.fragments.RoomTidinessListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.student.recycleradapters.RoomTidinessRecyclerAdapter

class RoomTidinessListFragment : RoomTidinessListFragment() {
    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return RoomTidinessRecyclerAdapter()
    }
}