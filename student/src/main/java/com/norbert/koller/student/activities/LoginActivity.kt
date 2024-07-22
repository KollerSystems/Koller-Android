package com.norbert.koller.student.activities

import android.content.Intent
import android.os.Bundle

class LoginActivity : com.norbert.koller.shared.activities.LoginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.body.buttonNoAccount.text = "Jelentkezés"

        binding.body.buttonNoAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}