package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.shared.R as Rs

class UsersFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : com.norbert.koller.shared.fragments.UsersFragment(defaultFilters) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton("Felhasználó felvétele") {

        }
    }

}