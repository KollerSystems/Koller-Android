package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.teacher.activities.CreateOutgoingActivity

class OutgoingPermanentFragment(userData: UserData? = null) : com.norbert.koller.shared.fragments.OutgoingPermanentFragment(userData) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        addButton(getString(R.string.add_continuous_outgoing)){
            CreateOutgoingActivity.type = CreateOutgoingActivity.PERMANENT
            CreateOutgoingActivity.userData = getBaseViewModel().owner
            val intent = Intent(requireContext(), CreateOutgoingActivity::class.java)
            requireContext().startActivity(intent)
        }

        super.onViewCreated(view, savedInstanceState)

    }

}