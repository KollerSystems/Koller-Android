package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.teacher.activities.CreateOutgoingActivity

class OutgoingPermanentListFragment(userData: UserData? = null) : com.norbert.koller.shared.fragments.OutgoingPermanentListFragment(userData) {

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