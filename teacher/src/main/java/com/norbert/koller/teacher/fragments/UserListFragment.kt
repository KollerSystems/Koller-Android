package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View

class UserListFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : com.norbert.koller.shared.fragments.UserListFragment(defaultFilters) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton("Felhasználó felvétele") {

        }
    }

}