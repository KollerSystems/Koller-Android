package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.teacher.EditUserActivity
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditRoomActivity
import com.norbert.koller.teacher.activities.RoomPresenceActivity
import com.norbert.koller.teacher.activities.RoomRateActivity

class RoomFragment(rid : Int? = null) : com.norbert.koller.shared.fragments.RoomFragment(rid)  {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scbRoomOrder : SimpleCardButton = view.findViewById(R.id.room_scb_room_order)
        scbRoomOrder.setOnClickListener{
            val intent = Intent(requireContext(), RoomRateActivity::class.java)
            startActivity(intent)
        }

        val scbPresence : SimpleCardButton = view.findViewById(R.id.room_scb_presence)
        scbPresence.setOnClickListener{
            val intent = Intent(requireContext(), RoomPresenceActivity::class.java)
            startActivity(intent)
        }

        val scbEdit : SimpleCardButton = view.findViewById(R.id.scb_edit)

        scbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditRoomActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_room
    }
}