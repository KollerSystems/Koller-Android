package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.databinding.ContentFragmentRoomHeaderBinding
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentRoomBinding


class RoomFragment(rid : Int? = null) : com.norbert.koller.shared.fragments.RoomFragment(rid) {

    lateinit var binding : FragmentRoomBinding
    override fun getHeaderBinding(): ContentFragmentRoomHeaderBinding {
        return binding.header
    }

    override fun createRootView(): View {
        binding = FragmentRoomBinding.inflate(layoutInflater)
        return binding.root
    }

}