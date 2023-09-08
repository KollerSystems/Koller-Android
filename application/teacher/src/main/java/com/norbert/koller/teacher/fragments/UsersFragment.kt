package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.shared.R as Rs

class UsersFragment : com.norbert.koller.shared.fragments.UsersFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonAddNew : Button = view.findViewById(Rs.id.button_add_new)

        val buttonLayout = buttonAddNew.parent as ViewGroup
        buttonLayout.visibility = VISIBLE
    }

}