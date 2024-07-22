package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditRoomActivity
import com.norbert.koller.teacher.activities.RoomPresenceActivity
import com.norbert.koller.teacher.activities.RoomRateActivity

class RoomFragment(rid : Int? = null) : com.norbert.koller.shared.fragments.RoomFragment(rid)  {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cbRoomOrder : CardButton = view.findViewById(R.id.room_cb_room_order)
        cbRoomOrder.setOnClickListener{
            val intent = Intent(requireContext(), RoomRateActivity::class.java)
            startActivity(intent)
        }

        val cbPresence : CardButton = view.findViewById(R.id.room_cb_presence)
        cbPresence.setOnClickListener{
            val intent = Intent(requireContext(), RoomPresenceActivity::class.java)
            startActivity(intent)
        }

        val cbEdit : CardButton = view.findViewById(R.id.cb_edit)

        cbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditRoomActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_room
    }
}