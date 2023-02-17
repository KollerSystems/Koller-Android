package com.example.koller

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById (R.id.login_button_login)
        val buttonNoAccount: Button = findViewById (R.id.login_button_no_account)
        val inpfID: TextInputEditText = findViewById(R.id.login_inpf_id)
        val inpfPassword: TextInputEditText = findViewById(R.id.login_inpf_password)
        val inplID: TextInputLayout = findViewById(R.id.login_inpl_id)
        val inplPassword: TextInputLayout = findViewById(R.id.login_inpl_password)

        fun checkInputsRefreshButton(){
            loginButton.isEnabled = (inpfID.text?.length ?: 0) > 0 && (inpfPassword.text?.length ?: 0) > 0
        }

        checkInputsRefreshButton()

        inpfID.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                inplID.error = null
                return@OnTouchListener false
            }
            false
        })


        inpfID.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
            }
        })

        inpfPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
            }
        })

        loginButton.setOnClickListener() {
            if((inpfID.text?.length ?: 0) == 11) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish();
                inplID.error = null
            }
            else{
                inplID.error = getString(R.string.invalid_id)
            }
        }

        loginButton.setOnLongClickListener(){
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)

            return@setOnLongClickListener true
        }

        buttonNoAccount.setOnClickListener() {
            Toast.makeText(this, "Hát az szopás", Toast.LENGTH_SHORT).show()
        }
    }
}