package com.example.koller.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.koller.R
import com.example.shared.data.UserData
import com.example.shared.fragments.UserOutgoingFragment
import com.example.shared.navigateWithDefaultAnimation

class ProfileBottomSheet : com.example.shared.fragments.bottomsheet.ProfileBottomSheet() {

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