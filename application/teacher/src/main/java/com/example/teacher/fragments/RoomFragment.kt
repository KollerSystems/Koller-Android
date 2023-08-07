package com.example.teacher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shared.SimpleCardButton
import com.example.shared.navigateWithDefaultAnimation
import com.example.teacher.R

class RoomFragment : com.example.shared.fragments.RoomFragment()  {


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
            findNavController().navigateWithDefaultAnimation(R.id.roomRateFragment)
        }
    }
}