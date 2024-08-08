package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.shared.databinding.ContentFragmentRoomHeaderBinding
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditRoomActivity
import com.norbert.koller.teacher.activities.RoomPresenceActivity
import com.norbert.koller.teacher.activities.RoomRateActivity
import com.norbert.koller.teacher.databinding.FragmentRoomBinding

class RoomFragment(rid : Int? = null) : com.norbert.koller.shared.fragments.RoomFragment(rid)  {

    lateinit var binding : FragmentRoomBinding
    override fun getHeaderBinding(): ContentFragmentRoomHeaderBinding {
        return binding.header
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbRoomTidiness.setOnClickListener{
            getMainActivity().addFragment(RoomTidinessFragment())
        }

        binding.cbPresence.setOnClickListener{
            val intent = Intent(requireContext(), RoomPresenceActivity::class.java)
            startActivity(intent)
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