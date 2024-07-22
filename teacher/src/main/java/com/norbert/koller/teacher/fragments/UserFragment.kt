package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.CrossingsFragment
import com.norbert.koller.shared.fragments.UserOutgoingsFragment
import com.norbert.koller.teacher.activities.EditUserActivity

class UserFragment(uid : Int? = null) : com.norbert.koller.shared.fragments.UserFragment(uid) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cbOutgoings : CardButton = view.findViewById(R.id.cb_outgoings)

        cbOutgoings.setOnClickListener {
            (context as MainActivity).addFragment(UserOutgoingsFragment(viewModel.response.value as UserData))
        }

        val cbGate : CardButton = view.findViewById(R.id.cb_gate)

        cbGate.setOnClickListener {
            (context as MainActivity).addFragment(CrossingsFragment(viewModel.id))
        }

        val cbEdit : CardButton = view.findViewById(R.id.cb_edit)

        cbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_user
    }
}