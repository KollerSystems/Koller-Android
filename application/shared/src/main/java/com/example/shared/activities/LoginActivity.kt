package com.example.shared.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.shared.DataStoreManager
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.api.APIInterface
import com.example.shared.api.RetrofitHelper
import com.example.shared.data.ApiErrorData
import com.example.shared.data.ApiLoginData
import com.example.shared.data.ApiLoginTokensData
import com.example.shared.data.UserData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class LoginActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var inplPassword: TextInputLayout
    lateinit var inplID: TextInputLayout
    var textIDNotForEveryone : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)


        loginButton = findViewById (R.id.login_button_login)
        val buttonNoAccount: Button = findViewById (R.id.login_button_no_account)
        inplID = findViewById(R.id.login_inpl_id)
        inplPassword = findViewById(R.id.login_inpl_password)

        fun checkInputsRefreshButton(){
            loginButton.isEnabled = (inplID.editText!!.text?.length ?: 0) > 0 && (inplPassword.editText!!.text?.length ?: 0) > 0
        }

        checkInputsRefreshButton()

        inplID.editText!!.addTextChangedListener(object : TextWatcher {

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

        inplPassword.editText!!.addTextChangedListener(object : TextWatcher {

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
            loadingBar.visibility = View.GONE
        }

        loginButton.setOnClickListener() {

            if(MyApplication.isOnline(this)){



                if((inplID.editText!!.text?.length ?: 0) > 0) {

                    loginLayout.alpha = 0.25f
                    loadingBar.visibility = View.VISIBLE

                    val loginResponse = RetrofitHelper.buildService(APIInterface::class.java)
                    val loginData = ApiLoginData("password", inplID.editText!!.text.toString(), inplPassword.editText!!.text.toString())
                    loginResponse.postLogin(loginData).enqueue(
                        object : Callback<ApiLoginTokensData> {

                            override fun onResponse(
                                call: Call<ApiLoginTokensData>,
                                loginResponse: Response<ApiLoginTokensData>
                            ) {
                                if(loginResponse.code() == 200) {

                                    ApiLoginTokensData.instance = loginResponse.body()!!

                                    lifecycleScope.launch {
                                        DataStoreManager.save(this@LoginActivity, DataStoreManager.REFRESH_TOKEN_NAME, ApiLoginTokensData.instance.refresh_token)
                                    }


                                    val userResponse = RetrofitHelper.buildService(APIInterface::class.java)
                                    userResponse.getCurrentUser(APIInterface.getHeaderMap()).enqueue(
                                        object : Callback<UserData> {
                                            override fun onResponse(
                                                call: Call<UserData>,
                                                userResponse: Response<UserData>
                                            ) {
                                                if(userResponse.code() == 200) {

                                                    UserData.instance = userResponse.body()!!

                                                    MyApplication.openMain.invoke(this@LoginActivity)
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

                                    var error : String?

                                    val gson = Gson()
                                    val type = object : TypeToken<ApiErrorData>() {}.type
                                    var errorResponse: ApiErrorData? = gson.fromJson(loginResponse.errorBody()!!.charStream(), type)

                                    APIInterface.ServerErrorPopup(this@LoginActivity, errorResponse!!.error)
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
                .setMessage(getString(textIDNotForEveryone))
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

            MyApplication.openMain.invoke(this@LoginActivity)
            finish()

            return@setOnLongClickListener true
        }

    }

}