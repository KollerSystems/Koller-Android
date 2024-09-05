package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ContentFragmentRoomHeaderBinding
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentRoomBinding


class RoomFragment : com.norbert.koller.shared.fragments.RoomFragment {

    constructor(id : Int? = null) : super(
        id, null
    )

    constructor(roomData : RoomData? = null) : super(
        null, roomData
    )

    lateinit var binding : FragmentRoomBinding
    override fun getHeaderBinding(): ContentFragmentRoomHeaderBinding {
        return binding.header
    }

    override fun createRootView(): View {
        binding = FragmentRoomBinding.inflate(layoutInflater)
        return binding.root
    }

}