package com.norbert.koller.shared.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
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
import android.window.OnBackInvokedDispatcher
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
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager.Companion.createDynamicUserDataStore
import com.norbert.koller.shared.managers.DataStoreManager.Companion.userDataStore
import com.norbert.koller.shared.managers.back
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.managers.getColorOfPixel
import com.norbert.koller.shared.recycleradapters.LoginViewPagerRecyclerAdapter
import com.norbert.koller.shared.viewmodels.LoginViewModel
import com.norbert.koller.shared.viewmodels.MainActivityViewModel
import com.stfalcon.imageviewer.common.extensions.isVisible
import kotlinx.coroutines.launch
import java.util.Calendar

abstract class LoginActivity : AppCompatActivity() {


    lateinit var viewModel: LoginViewModel
    lateinit var binding : ActivityLoginBinding

    fun changeLoadingStateTo(isEnabled : Boolean){
        binding.cardLoading.isVisible = isEnabled
    }

    private fun handleBackPress(){
        if(binding.body.viewPager.currentItem == 1){
            binding.body.viewPager.back()
        }
        else{
            finish()
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        handleBackPress()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ){
                handleBackPress()
            }
        }
    }

    abstract fun getLoginViewModel() : LoginViewModel

    abstract fun getRecyclerAdapter() : LoginViewPagerRecyclerAdapter

    abstract fun getRoleError() : Int

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.body.viewPager.adapter = getRecyclerAdapter()
        binding.body.viewPager.isUserInputEnabled = false

        viewModel = getLoginViewModel()

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

        viewModel.loading.observe(this){loading ->
            if(loading){
                changeLoadingStateTo(true)
            }
            else{
                changeLoadingStateTo(false)
            }
        }

        viewModel.userData.observe(this){
            it as UserData
            if(CacheManager.userData?.uid != it.uid){
                CacheManager.detailsDataMap = mutableMapOf()
                CacheManager.listDataMap = mutableMapOf()
            }
            CacheManager.userData = it
            CacheManager.userData!!.saveReceivedTime()
            if(!userDataStore.containsKey(it.uid)){
                createDynamicUserDataStore(CacheManager.loginData!!.uid)
            }

            lifecycleScope.launch {
                DataStoreManager.saveTokens(this@LoginActivity)
            }

            ApplicationManager.openMain.invoke(this@LoginActivity)
            finish()
        }

        viewModel.getUserError.observe(this){
            if(it == null) return@observe

            APIInterface.serverErrorPopup(this@LoginActivity, it){
                viewModel.getUserError.value = null
            }
        }

        viewModel.getRoleError.observe(this){
            if(viewModel.getRoleError.value == false) return@observe
            MaterialAlertDialogBuilder(this)
                .setTitle("Téves alkalmazás vagy felhasználó")
                .setMessage(getString(getRoleError()))
                .setPositiveButton(
                    getString(R.string.ok)
                )
                { _, _ ->

                }
                .setOnDismissListener {
                    viewModel.getRoleError.value = false
                }
                .show()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        binding.root.post{
            binding.body.viewPager.layoutParams.height = binding.body.viewPager.height
        }

    }
}