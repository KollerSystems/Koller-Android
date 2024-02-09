package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.fragments.UserExitsAndEntrancesFragment
import com.norbert.koller.shared.fragments.UserOutgoingsFragment
import com.norbert.koller.teacher.EditUserActivity
import com.norbert.koller.teacher.activities.RoomPresenceActivity

class UserFragment(uid : Int? = null) : com.norbert.koller.shared.fragments.UserFragment(uid) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scbOutgoings : SimpleCardButton = view.findViewById(R.id.scb_outgoings)

        scbOutgoings.setOnClickListener {
            (context as MainActivity).addFragment(UserOutgoingsFragment())
        }

        val scbGate : SimpleCardButton = view.findViewById(R.id.scb_gate)

        scbGate.setOnClickListener {
            (context as MainActivity).addFragment(UserExitsAndEntrancesFragment(viewModel.id))
        }

        val scbEdit : SimpleCardButton = view.findViewById(R.id.scb_edit)

        scbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_user
    }
}