package com.norbert.koller.student.activities

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.norbert.koller.shared.activities.ManageKeyActivity
import com.norbert.koller.student.fragments.dialog.AcquiredKeyDFragment

class ManageKeyActivity : ManageKeyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        getConfirmButton().setOnClickListener{
            var dialog = AcquiredKeyDFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction
                .add(android.R.id.content, dialog)
                .addToBackStack(null)
                .commit()
        }
    }



}