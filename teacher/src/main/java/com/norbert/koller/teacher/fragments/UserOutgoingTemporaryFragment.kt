package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.teacher.activities.CreateOutgoingActivity

class UserOutgoingTemporaryFragment(userData: UserData? = null) : com.norbert.koller.shared.fragments.UserOutgoingTemporaryFragment(userData) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonAddNew : Button = view.findViewById(R.id.button_add_new)

        val buttonLayout = buttonAddNew.parent as ViewGroup
        buttonLayout.visibility = View.VISIBLE

        buttonAddNew.setOnClickListener{
            CreateOutgoingActivity.type = CreateOutgoingActivity.TEMPORARY
            CreateOutgoingActivity.userData = userData
            val intent = Intent(requireContext(), CreateOutgoingActivity::class.java)
            requireContext().startActivity(intent)
        }

    }

}