package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.norbert.koller.shared.databinding.ContentFragmentRoomHeaderBinding
import com.norbert.koller.shared.fragments.RoomTidinessListFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.teacher.activities.EditRoomActivity
import com.norbert.koller.teacher.databinding.FragmentRoomBinding
import okhttp3.Cache

class RoomFragment() : com.norbert.koller.shared.fragments.RoomFragment() {

    lateinit var binding : FragmentRoomBinding
    override fun getHeaderBinding(): ContentFragmentRoomHeaderBinding {
        return binding.header
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.header.textManageRoom.isVisible = true
        binding.header.cbRoomTidiness.isVisible = true

        binding.cbPresence.setOnClickListener{
            getMainActivity().addFragment(RoomPresenceListFragment())
        }

        binding.cbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditRoomActivity::class.java)
            startActivity(intent)
        }

    }

    override fun createRootView(): View {
        binding = FragmentRoomBinding.inflate(layoutInflater)
        return binding.root
    }
}