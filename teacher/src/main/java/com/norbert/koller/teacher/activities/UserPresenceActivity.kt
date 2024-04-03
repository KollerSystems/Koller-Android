package com.norbert.koller.teacher.activities

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.managers.setToolbarToViewColor
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R

class UserPresenceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_presence)

        val appBar : AppBarLayout = findViewById(R.id.appbar)

        appBar.setup()

        window.setToolbarToViewColor(findViewById(R.id.bottom_view))

        val backButton : Button = findViewById(R.id.toolbar_exit)

        backButton.setOnClickListener{
            onBackPressed()
        }
    }

}