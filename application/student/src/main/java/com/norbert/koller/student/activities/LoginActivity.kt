package com.norbert.koller.student.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.norbert.koller.student.BuildConfig
import com.norbert.koller.student.MyApplication
import com.norbert.koller.student.R
import com.norbert.koller.shared.R as Rs

class LoginActivity : com.norbert.koller.shared.activities.LoginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buttonNoAccount.text = "Jelentkezés"

        buttonNoAccount.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}