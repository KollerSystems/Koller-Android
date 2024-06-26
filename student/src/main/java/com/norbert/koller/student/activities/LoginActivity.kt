package com.norbert.koller.student.activities

import android.content.Intent
import android.os.Bundle
import com.norbert.koller.shared.activities.WelcomeActivity

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