package com.norbert.koller.teacher.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R

class EditRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_edit_room)

        val appBar : AppBarLayout = findViewById(R.id.app_bar)

        appBar.setup()

        val backButton : Button = findViewById(R.id.button_back)

        backButton.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}