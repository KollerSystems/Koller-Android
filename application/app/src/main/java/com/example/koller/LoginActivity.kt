package com.example.koller

import APIInterface
import ApiLoginTokensData
import RetrofitHelper
import UserData
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private fun getHeaderMap(): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Content-Type"] = "application/json"
        headerMap["Authorization"] = "Bearer ${ApiLoginTokensData.instance.access_token}"
        Log.d("INFO", ApiLoginTokensData.instance.access_token)
        return headerMap
    }

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
            if((inpfID.text?.length ?: 0) > 0) {

                val loginResponse = RetrofitHelper.buildService(APIInterface::class.java)
                loginResponse.postLogin(ApiLoginData("password", inpfID.text.toString(), inpfPassword.text.toString())).enqueue(
                    object : Callback<ApiLoginTokensData> {
                        override fun onResponse(
                            call: Call<ApiLoginTokensData>,
                            loginResponse: Response<ApiLoginTokensData>
                        ) {
                            if(loginResponse.code() == 200) {

                                ApiLoginTokensData.instance = loginResponse.body()!!

                                Log.d("ERROR", "Access token: "+ApiLoginTokensData.instance.access_token)
                                
                                val userResponse = RetrofitHelper.buildService(APIInterface::class.java)
                                userResponse.getCurrentUser(getHeaderMap()).enqueue(
                                    object : Callback<UserData> {
                                        override fun onResponse(
                                            call: Call<UserData>,
                                            userResponse: Response<UserData>
                                        ) {
                                            Log.d("ERROR", "Username error num: "+userResponse.code())
                                            Log.d("ERROR", "Header: "+getHeaderMap())
                                            if(userResponse.code() == 200) {

                                                UserData.instance = userResponse.body()!!
                                                Log.d("ERROR", "Username: "+UserData.instance.Name)
                                            }
                                        }

                                        override fun onFailure(call: Call<UserData>, t: Throwable) {
                                            Log.d("ERROR", "Username error: " + t.message)
                                        }
                                    }
                                )

                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish();
                            }
                            else{
                                inplID.error = getString(R.string.invalid_id)
                            }
                        }

                        override fun onFailure(call: Call<ApiLoginTokensData>, t: Throwable) {
                            inplID.error = "Hoppá! A szerver oldalán valami nem stimmel"
                        }
                    }
                )
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