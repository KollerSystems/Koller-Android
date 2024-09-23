package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View

class UserListFragment() : com.norbert.koller.shared.fragments.UserListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton("Felhasználó felvétele") {

        }
    }

}