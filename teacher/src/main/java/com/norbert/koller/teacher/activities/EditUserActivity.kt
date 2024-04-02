package com.norbert.koller.teacher.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R

class EditUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user)

        val appBar : AppBarLayout = findViewById(R.id.appbar)

        appBar.setup()

        val backButton : Button = findViewById(R.id.toolbar_exit)

        backButton.setOnClickListener{
            onBackPressed()
        }

    }
}