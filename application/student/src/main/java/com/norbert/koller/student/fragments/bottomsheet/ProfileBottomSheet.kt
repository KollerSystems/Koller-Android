package com.norbert.koller.student.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.student.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UserOutgoingsFragment

class ProfileBottomSheet : com.norbert.koller.shared.fragments.bottomsheet.ProfileBottomSheet() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_sheet_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fbtnOutgoing: View = view.findViewById(R.id.profile_fbtn_outgoing)

        fbtnOutgoing.setOnClickListener{

            UserOutgoingsFragment.open(requireContext(), UserData.instance.UID)
            this.dismiss()
        }
    }

}