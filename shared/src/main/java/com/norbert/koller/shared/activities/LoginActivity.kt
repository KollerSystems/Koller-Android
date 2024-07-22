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
import com.norbert.koller.shared.databinding.ActivityLoginBinding
import com.norbert.koller.shared.databinding.ActivityMainBinding
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.managers.getColorOfPixel
import com.norbert.koller.shared.viewmodels.LoginViewModel
import com.norbert.koller.shared.viewmodels.MainActivityViewModel
import com.stfalcon.imageviewer.common.extensions.isVisible
import kotlinx.coroutines.launch
import java.util.Calendar

open class LoginActivity : AppCompatActivity() {


    lateinit var viewModel: LoginViewModel
    lateinit var binding : ActivityLoginBinding

    fun checkInputsRefreshButton(){
        binding.body.buttonLogin.isEnabled = (binding.body.tietUsername.text?.length ?: 0) > 0 && (binding.body.tietPassword.text?.length ?: 0) > 0
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val isLandscape = (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)


        if(!isLandscape){
            window.navigationBarColor = this.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerLow)
        }
        else{
            val root : ViewGroup = binding.cardBody.parent as ViewGroup
            window.navigationBarColor = Color.TRANSPARENT

            root.post{

                if(root.width / 3f > binding.cardBody.width){
                    val layoutParams = binding.cardBody.layoutParams as LinearLayout.LayoutParams
                    layoutParams.weight = 1f
                    layoutParams.width = 0
                    binding.cardBody.layoutParams = layoutParams
                }

            }

        }


        checkInputsRefreshButton()


        fun changeLoadingStateTo(isEnabled : Boolean){
            binding.cardLoading.isVisible = isEnabled
        }

        binding.body.buttonLogin.setOnClickListener {

            if(ApplicationManager.isOnline(this)){

                if((binding.body.tietUsername.text?.length ?: 0) > 0) {

                    val loginData = ApiLoginUsernameAndPasswordData("password", binding.body.tietUsername.text.toString(), binding.body.tietPassword.text.toString())
                    viewModel.login(loginData)

                }
                else{
                    binding.body.tilUsername.error = getString(R.string.invalid_id)
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


        binding.body.buttonForgotPassword.setOnClickListener{
            if(binding.body.tietUsername.text.isNullOrBlank()){
                binding.body.tilUsername.error = getString(R.string.mandatory_field)
            }
            else if(false){
                binding.body.tilUsername.error = getString(R.string.invalid_id)
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


        binding.body.buttonLogin.setOnLongClickListener {
            Toast.makeText(this, "Csak téged csak most kivételesen beengedlek", Toast.LENGTH_SHORT).show()

            ApplicationManager.openMain.invoke(this@LoginActivity)
            finish()

            return@setOnLongClickListener true
        }

        viewModel.loading.observe(this){loading ->
            if(loading){
                changeLoadingStateTo(true)
            }
            else{
                changeLoadingStateTo(false)
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
                    binding.body.tilUsername.error = getString(R.string.invalid_id)
                }
                "invalid_password" ->{
                    binding.body.tilPassword.error = getString(R.string.invalid_password)
                }
                "-" -> {
                    APIInterface.serverErrorPopup(this@LoginActivity, it){
                        viewModel.postLoginError.value = null
                    }
                }
                null ->{
                    binding.body.tilUsername.error = null
                    binding.body.tilPassword.error = null
                }
            }
        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        binding.body.tietUsername.addTextChangedListener(object : TextWatcher {

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

        binding.body.tilPassword.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
                if(!binding.body.tilPassword.error.isNullOrEmpty()) {
                    viewModel.postLoginError.value = null
                }
            }
        })
    }

}