package com.norbert.koller.student.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.student.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UserOutgoingsFragment

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

        val fbtnOutgoing: View = view.findViewById(R.id.profile_fbtn_outgoing)

        fbtnOutgoing.setOnClickListener{

            (requireContext() as MainActivity).addFragment(UserOutgoingsFragment(UserData.instance.UID))
            this.dismiss()
        }
    }

}