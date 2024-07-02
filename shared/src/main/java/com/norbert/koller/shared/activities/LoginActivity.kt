package com.norbert.koller.shared.activities

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.data.UserData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.api.AuthenticationManager
import com.norbert.koller.shared.data.ApiLoginUsernameAndPasswordData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.managers.getColorOfPixel
import com.norbert.koller.shared.viewmodels.LoginViewModel
import com.norbert.koller.shared.viewmodels.MainActivityViewModel
import com.stfalcon.imageviewer.common.extensions.isVisible
import kotlinx.coroutines.launch
import java.util.Calendar

open class LoginActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var buttonNoAccount: Button
    lateinit var inplPassword: TextInputLayout
    lateinit var inplID: TextInputLayout
    lateinit var buttonForgotPassword : Button

    lateinit var viewModel: LoginViewModel

    fun checkInputsRefreshButton(){
        loginButton.isEnabled = (inplID.editText!!.text?.length ?: 0) > 0 && (inplPassword.editText!!.text?.length ?: 0) > 0
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val textVersion : TextView = findViewById(R.id.text_version)
        val bottomView : View = findViewById(R.id.bottom_view)
        val isLandscape = (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        loginButton = findViewById (R.id.login_button_login)
        buttonNoAccount = findViewById (R.id.login_button_no_account)
        inplID = findViewById(R.id.login_inpl_id)
        inplPassword = findViewById(R.id.login_inpl_password)
        val loginLayout : View = findViewById(R.id.login_linear_login)
        val loadingBar : View = findViewById(R.id.login_loading)

        textVersion.text = ApplicationManager.version

        if(!isLandscape){
            window.navigationBarColor = this.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerLow)
        }
        else{
            val root : ViewGroup = bottomView.parent as ViewGroup
            window.navigationBarColor = Color.TRANSPARENT

            root.post{

                if(root.width / 3f > bottomView.width){
                    val layoutParams = bottomView.layoutParams as LinearLayout.LayoutParams
                    layoutParams.weight = 1f
                    layoutParams.width = 0
                    bottomView.layoutParams = layoutParams
                }

            }

        }


        checkInputsRefreshButton()


        fun changeLoadingStateTo(isEnabled : Boolean){
            loadingBar.isVisible = isEnabled
            inplPassword.editText!!.isEnabled = !isEnabled
            inplID.editText!!.isEnabled = !isEnabled
            buttonNoAccount.isClickable = !isEnabled
            loginButton.isClickable = !isEnabled
            loginButton.isLongClickable = !isEnabled
            buttonForgotPassword.isClickable = !isEnabled
        }

        fun returnLoginLayoutToNormal(){
            loginLayout.alpha = 1f
            changeLoadingStateTo(false)
        }

        loginButton.setOnClickListener {

            if(ApplicationManager.isOnline(this)){

                if((inplID.editText!!.text?.length ?: 0) > 0) {

                    val loginData = ApiLoginUsernameAndPasswordData("password", inplID.editText!!.text.toString(), inplPassword.editText!!.text.toString())
                    viewModel.login(loginData)

                }
                else{
                    inplID.error = getString(R.string.invalid_id)
                }
            }
            else{
                MaterialAlertDialogBuilder(this@LoginActivity)
                    .setTitle("Nincs internet!")
                    .setIcon(R.drawable.no_internet)
                    .setPositiveButton(
                        getString(R.string.ok)
                    )
                    { _, _ ->

                    }
                    .show()
            }
        }


        buttonForgotPassword = findViewById(R.id.login_button_forgot_password)
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


        loginButton.setOnLongClickListener {
            Toast.makeText(this, "Csak téged csak most kivételesen beengedlek", Toast.LENGTH_SHORT).show()

            ApplicationManager.openMain.invoke(this@LoginActivity)
            finish()

            return@setOnLongClickListener true
        }

        viewModel.loading.observe(this){loading ->
            if(loading){
                loginLayout.alpha = 0.25f
                changeLoadingStateTo(true)
            }
            else{
                returnLoginLayoutToNormal()
            }
        }

        viewModel.userData.observe(this){
            lifecycleScope.launch {
                DataStoreManager.save(this@LoginActivity, LoginTokensData.instance!!)
            }

            UserData.instance = it as UserData

            ApplicationManager.openMain.invoke(this@LoginActivity)
            finish()
        }

        viewModel.getUserError.observe(this){
            if(it == null) return@observe

            APIInterface.serverErrorPopup(this@LoginActivity, it){
                viewModel.getUserError.value = null
            }
        }

        viewModel.postLoginError.observe(this){
            when(it){
                "invalid_username" ->{
                    inplID.error = getString(R.string.invalid_id)
                }
                "invalid_password" ->{
                    inplPassword.error = getString(R.string.invalid_password)
                }
                "-" -> {
                    APIInterface.serverErrorPopup(this@LoginActivity, it){
                        viewModel.postLoginError.value = null
                    }
                }
                null ->{
                    inplID.error = null
                    inplPassword.error = null
                }
            }
        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        inplID.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
                viewModel.postLoginError.value = null
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
                if(!inplPassword.error.isNullOrEmpty()) {
                    viewModel.postLoginError.value = null
                }
            }
        })
    }

}