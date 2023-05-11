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
import android.view.View
import android.view.View.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        inpfID.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
                inplID.error = ""
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
                inplPassword.error = ""
            }
        })

        val loginLayout : LinearLayout = findViewById(R.id.login_linear_login)
        val loadingBar : View = findViewById(R.id.login_loading)

        fun ReturnLoginLayoutToNormal(){
            loginLayout.alpha = 1f
            loadingBar.visibility = GONE
        }

        loginButton.setOnClickListener() {

            if(MyApplication.isOnline(this)){



                if((inpfID.text?.length ?: 0) > 0) {

                    loginLayout.alpha = 0.25f
                    loadingBar.visibility = VISIBLE

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
                                    userResponse.getCurrentUser(APIInterface.getHeaderMap()).enqueue(
                                        object : Callback<UserData> {
                                            override fun onResponse(
                                                call: Call<UserData>,
                                                userResponse: Response<UserData>
                                            ) {
                                                if(userResponse.code() == 200) {

                                                    UserData.instance = userResponse.body()!!

                                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                                    startActivity(intent)
                                                    finish()
                                                }
                                                else{
                                                    ReturnLoginLayoutToNormal()
                                                    APIInterface.ServerErrorPopup(this@LoginActivity, userResponse.code().toString())
                                                }
                                            }

                                            override fun onFailure(call: Call<UserData>, t: Throwable) {
                                                ReturnLoginLayoutToNormal()
                                                APIInterface.ServerErrorPopup(this@LoginActivity, t.localizedMessage)
                                            }
                                        }
                                    )
                                }
                                else{
                                    ReturnLoginLayoutToNormal()
                                    inplID.error = getString(R.string.invalid_id)
                                    inplPassword.error = getString(R.string.invalid_password)
                                }
                            }

                            override fun onFailure(call: Call<ApiLoginTokensData>, t: Throwable) {
                                ReturnLoginLayoutToNormal()
                                APIInterface.ServerErrorPopup(this@LoginActivity)
                            }
                        }
                    )
                }
                else{
                    inplID.error = getString(R.string.invalid_id)
                }
            }
            else{
                MaterialAlertDialogBuilder(this@LoginActivity)
                    .setTitle("Nincs internet DUMASS!")
                    .setPositiveButton(
                        getString(R.string.ok)
                    )
                    { _, _ ->

                    }
                    .show()
            }
        }

        loginButton.setOnLongClickListener(){
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)

            return@setOnLongClickListener true
        }


        val buttonForgotPassword : Button = findViewById(R.id.login_button_forgot_password)
        buttonForgotPassword.setOnClickListener{
            if(inplID.editText!!.text.isNullOrBlank()){
                inplID.error = getString(R.string.mandatory_field)
            }
            else if(false){
                inplID.error = getString(R.string.invalid_id)
            }
            else{
                MaterialAlertDialogBuilder(this@LoginActivity)
                    .setTitle(getString(R.string.successfully_sent))
                    .setMessage(getString(R.string.success_email_sent_desc, "kisgazsi@gmail.com"))
                    .setPositiveButton(
                        getString(R.string.ok)
                    )
                    { _, _ ->

                    }
                    .setNeutralButton(getString(R.string.open_emails))
                    { _, _ ->
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        this.startActivity(intent)
                    }
                    .show()
            }
        }

        buttonNoAccount.setOnClickListener{

            MaterialAlertDialogBuilder(this@LoginActivity)
                .setTitle(getString(R.string.this_app_is_not_for_everyone))
                .setMessage(getString(R.string.this_app_is_not_for_everyone_desc))
                .setPositiveButton(
                    getString(R.string.understood)
                )
                { _, _ ->

                }
                .setNeutralButton(getString(R.string.open_emails))
                { _, _ ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this.startActivity(intent)
                }
                .show()
        }

        buttonNoAccount.setOnLongClickListener {
            Toast.makeText(this, "Csak téged csak most kivételesen beengedlek", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            return@setOnLongClickListener true
        }
    }
}