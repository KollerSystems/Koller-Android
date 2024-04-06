package com.norbert.koller.teacher.activities

import android.content.Intent
import android.os.Bundle
import com.norbert.koller.shared.activities.CreateNewPostActivity
import com.norbert.koller.teacher.R

class SettingsActivity : com.norbert.koller.shared.activities.SettingsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)



    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        openAnActivity.setOnClickListener {
            val intent = Intent(this, EditStudyGroupActivity::class.java)
            startActivity(intent)
        }
    }

}