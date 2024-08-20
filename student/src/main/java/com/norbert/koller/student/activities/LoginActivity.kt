package com.norbert.koller.student.activities

import android.content.Intent
import android.os.Bundle
import com.norbert.koller.student.R

class LoginActivity : com.norbert.koller.shared.activities.LoginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.body.buttonNoAccount.text = getString(com.norbert.koller.shared.R.string.sign_up)

        binding.body.buttonNoAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}