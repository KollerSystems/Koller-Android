package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.teacher.activities.CreateOutgoingActivity

class OutgoingPermanentListFragment() : com.norbert.koller.shared.fragments.OutgoingPermanentListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        addButton(getString(R.string.add_continuous_outgoing)){
            val intent = Intent(requireContext(), CreateOutgoingActivity::class.java)
            intent.putExtra("type", CreateOutgoingActivity.PERMANENT)
            intent.putExtra("id", getBaseViewModel().ownerUID)
            requireContext().startActivity(intent)
        }

        super.onViewCreated(view, savedInstanceState)

    }

}