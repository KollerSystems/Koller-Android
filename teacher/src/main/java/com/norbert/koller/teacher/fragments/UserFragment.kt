package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.fragments.UserExitsAndEntrancesFragment
import com.norbert.koller.shared.fragments.UserOutgoingsFragment

class UserFragment(UID : Int? = null) : com.norbert.koller.shared.fragments.UserFragment(UID) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scbOutgoings : SimpleCardButton = view.findViewById(R.id.scb_outgoings)

        scbOutgoings.setOnClickListener {
            (context as MainActivity).addFragment(UserOutgoingsFragment())
        }

        val scbGate : SimpleCardButton = view.findViewById(R.id.scb_gate)

        scbGate.setOnClickListener {
            (context as MainActivity).addFragment(UserExitsAndEntrancesFragment(viewModel.ID))
        }

        val scbEdit : SimpleCardButton = view.findViewById(R.id.scb_edit)

        scbEdit.setOnClickListener {

        }
    }
}