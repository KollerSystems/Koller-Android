package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.SimpleCardButton
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.RoomRateActivity

class RoomFragment : com.norbert.koller.shared.fragments.RoomFragment()  {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scbRoomOrder : SimpleCardButton = view.findViewById(R.id.room_scb_room_order)
        scbRoomOrder.setOnClickListener{
            val intent = Intent(requireContext(), RoomRateActivity::class.java)
            startActivity(intent)
        }
    }
}