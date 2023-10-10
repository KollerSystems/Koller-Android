package com.norbert.koller.shared

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ManageAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_account)

        val backButton : Button = findViewById(R.id.toolbar_exit)

        backButton.setOnClickListener{
            finish()
        }
    }
}