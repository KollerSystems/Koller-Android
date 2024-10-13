package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ContentFragmentRoomHeaderBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentRoomBinding


class RoomFragment() : com.norbert.koller.shared.fragments.RoomFragment() {


    lateinit var binding : FragmentRoomBinding
    override fun getHeaderBinding(): ContentFragmentRoomHeaderBinding {
        return binding.header
    }

    override fun createRootView(): View {
        binding = FragmentRoomBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(viewModel.id == CacheManager.userData!!.rid){
            binding.header.textManageRoom.isVisible = true
            binding.header.textManageRoom.text = getString(com.norbert.koller.shared.R.string.menu)
            binding.header.cbRoomTidiness.isVisible = true
            RecyclerViewHelper.roundCard(binding.header.cbRoomTidiness)
        }
    }

}