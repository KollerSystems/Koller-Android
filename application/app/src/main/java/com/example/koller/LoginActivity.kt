package com.example.koller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById (R.id.login_button)

        loginButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonNoAccount: Button = findViewById (R.id.button_no_account)

        buttonNoAccount.setOnClickListener() {
            Toast.makeText(this, "Hát az szopás", Toast.LENGTH_SHORT).show()
        }
    }
}