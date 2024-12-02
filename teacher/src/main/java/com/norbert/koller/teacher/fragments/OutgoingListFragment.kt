package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.teacher.activities.CreateOutgoingActivity

class OutgoingListFragment() : com.norbert.koller.shared.fragments.OutgoingListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButton(getString(com.norbert.koller.teacher.R.string.add_outgoing)){
            val intent = Intent(requireContext(), CreateOutgoingActivity::class.java)
            intent.putExtra("id", getBaseViewModel().ownerUID)
            requireContext().startActivity(intent)
        }
    }
}