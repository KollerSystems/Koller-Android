package com.norbert.koller.student.activities

import android.os.Bundle
import com.norbert.koller.student.R


class SettingsActivity : com.norbert.koller.shared.activities.SettingsActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

}