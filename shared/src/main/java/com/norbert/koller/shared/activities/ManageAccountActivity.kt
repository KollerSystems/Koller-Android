package com.norbert.koller.shared.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.norbert.koller.shared.R

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