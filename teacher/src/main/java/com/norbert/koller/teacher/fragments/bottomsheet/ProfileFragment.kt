package com.norbert.koller.teacher.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.fragments.UsersFragment
import com.norbert.koller.teacher.R

class ProfileFragment : com.norbert.koller.shared.fragments.bottomsheet.ProfileFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_bshd_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scb : SimpleCardButton = view.findViewById(R.id.profile_card_my_groups)
        val mainActivity = context as MainActivity

        scb.setOnClickListener {

            val usersFragment = UsersFragment()
            mainActivity.addFragment(usersFragment)
        }

    }

}