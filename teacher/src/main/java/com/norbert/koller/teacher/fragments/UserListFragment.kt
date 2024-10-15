package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.FilterConfigData
import com.norbert.koller.shared.managers.ApplicationManager

class UserListFragment() : com.norbert.koller.shared.fragments.UserListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButton("Felhasználó felvétele") {
            val fragment = ApplicantListFragment()
            val bundle = Bundle()
            bundle.putParcelable("filters", FilterConfigData(mutableMapOf(Pair("Type", arrayListOf("0")))))
            fragment.arguments = bundle
            (context as MainActivity).addFragment(fragment)
        }
    }

}