package com.norbert.koller.student.activities

import android.content.Intent
import android.os.Bundle
import com.norbert.koller.student.R

class LoginActivity : com.norbert.koller.shared.activities.LoginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textIDNotForEveryone = R.string.this_app_is_not_for_everyone_desc

        loginButton.setOnLongClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)

            return@setOnLongClickListener true
        }
    }
}