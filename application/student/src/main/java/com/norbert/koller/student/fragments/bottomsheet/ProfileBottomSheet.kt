package com.norbert.koller.student.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.student.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UserOutgoingFragment

class ProfileBottomSheet : com.norbert.koller.shared.fragments.bottomsheet.ProfileBottomSheet() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realView = inflater.inflate(R.layout.bottom_sheet_profile, container, false)

        super.onCreateView(inflater, container, savedInstanceState)

        val fbtnOutgoing: View = realView.findViewById(R.id.profile_fbtn_outgoing)

        fbtnOutgoing.setOnClickListener{

            UserOutgoingFragment.open(requireContext(), UserData.instance.UID)
            this.dismiss()
        }

        return  realView
    }

}