package com.norbert.koller.student.activities

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.norbert.koller.shared.activities.ManageKeyActivity
import com.norbert.koller.shared.managers.ApplicationManager

class ManageKeyActivity : ManageKeyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        getConfirmButton().setOnClickListener{
            ApplicationManager.openActivity(this, AcquiredKeyActivity::class.java)
            finish()
        }
    }



}