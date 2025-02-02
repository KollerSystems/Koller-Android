package com.norbert.koller.teacher.fragments

import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.KeyAccessListFragment

import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.teacher.activities.CreateOutgoingActivity

class KeyAccessListFragment : KeyAccessListFragment() {

    override fun onSetUpSearching() {
        super.onSetUpSearching()
        addButton(getString(R.string.give_access)){
            ApplicationManager.openActivity(requireContext(), CreateOutgoingActivity::class.java)
        }
    }

}