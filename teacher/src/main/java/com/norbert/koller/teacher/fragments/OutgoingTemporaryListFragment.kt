package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.teacher.activities.CreateOutgoingActivity

class OutgoingTemporaryListFragment() : com.norbert.koller.shared.fragments.OutgoingTemporaryListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        addButton(getString(R.string.add_temporary_outgoing)){
            val intent = Intent(requireContext(), CreateOutgoingActivity::class.java)
            intent.putExtra("type", CreateOutgoingActivity.TEMPORARY)
            intent.putExtra("id", getBaseViewModel().ownerUID)
            requireContext().startActivity(intent)
        }

        super.onViewCreated(view, savedInstanceState)
    }

}