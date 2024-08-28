package com.norbert.koller.shared.recycleradapters

import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View.IMPORTANT_FOR_AUTOFILL_NO
import android.view.View.IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.data.ApiLoginUsernameAndPasswordData
import com.norbert.koller.shared.databinding.ContentActivityLoginBodyContentBinding
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.back
import com.norbert.koller.shared.managers.next


abstract class LoginViewPagerRecyclerAdapter : RecyclerView.Adapter<LoginViewPagerRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding = ContentActivityLoginBodyContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemBinding = holder.itemBinding
        val activity = holder.itemBinding.root.context as LoginActivity
        when(position){
            0->{
                passwordHolder(itemBinding, activity)
            }
            1->{
                twoFAHolder(itemBinding, activity)
            }
        }
    }

    fun checkInputsRefreshButton(binding : ContentActivityLoginBodyContentBinding){
        binding.buttonPrimary.isEnabled = (binding.tilFirst.editText!!.text?.length ?: 0) > 0 && (binding.tilSecond.editText!!.text?.length ?: 0) > 0
    }

    open fun passwordHolder(binding : ContentActivityLoginBodyContentBinding, activity : LoginActivity){
        binding.buttonPrimary.text = activity.getString(R.string.login)
        binding.tilSecond.hint = activity.getString(R.string.password)
        binding.tilSecond.editText!!.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        binding.tilSecond.endIconMode = END_ICON_PASSWORD_TOGGLE
        binding.btnForgot.text = activity.getString(R.string.forgotten_password)
        binding.text.text = activity.getString(R.string.please_login)
        binding.tilSecond.editText!!.transformationMethod = PasswordTransformationMethod()
        binding.tilFirst.editText!!.setText("tweinek5")
        binding.tilSecond.editText!!.setText("porcica1")
        binding.tilFirst.editText!!.importantForAutofill = IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS
        binding.tilSecond.editText!!.importantForAutofill = IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS

        binding.buttonSecondary.setOnLongClickListener{
            activity.binding.body.viewPager.next()
            return@setOnLongClickListener true
        }

        binding.buttonPrimary.setOnClickListener {

            if(ApplicationManager.isOnline(activity)){

                if((binding.tilFirst.editText!!.text?.length ?: 0) > 0) {

                    val loginData = ApiLoginUsernameAndPasswordData("password", binding.tilFirst.editText!!.text.toString(), binding.tilSecond.editText!!.text.toString())
                    activity.viewModel.login(loginData)

                }
                else{
                    binding.tilFirst.error = activity.getString(R.string.invalid_id)
                }
            }
            else{
                MaterialAlertDialogBuilder(activity)
                    .setTitle("Nincs internet!")
                    .setIcon(R.drawable.no_internet)
                    .setPositiveButton(
                        activity.getString(R.string.ok)
                    )
                    { _, _ ->

                    }
                    .show()
            }
        }


        binding.btnForgot.setOnClickListener{
            if(binding.tilFirst.editText!!.text.isNullOrBlank()){
                binding.tilFirst.error = activity.getString(R.string.mandatory_field)
            }
            else if(false){
                binding.tilFirst.error = activity.getString(R.string.invalid_id)
            }
            else{
                MaterialAlertDialogBuilder(activity)
                    .setTitle(activity.getString(R.string.successfully_sent))
                    .setMessage(activity.getString(R.string.success_email_sent_desc, "kisgazsi@gmail.com"))
                    .setPositiveButton(
                        activity.getString(R.string.ok)
                    )
                    { _, _ ->

                    }
                    .setNeutralButton(activity.getString(R.string.open_emails))
                    { _, _ ->
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        activity.startActivity(intent)
                    }
                    .show()
            }
        }


        binding.buttonPrimary.setOnLongClickListener {
            Toast.makeText(activity, "Csak téged csak most kivételesen beengedlek", Toast.LENGTH_SHORT).show()

            ApplicationManager.openMain.invoke(activity)
            activity.finish()

            return@setOnLongClickListener true
        }

        activity.viewModel.postLoginError.observe(activity){
            when(it){
                "invalid_username" ->{
                    binding.tilFirst.error = activity.getString(R.string.invalid_id)
                }
                "invalid_password" ->{
                    binding.tilSecond.error = activity.getString(R.string.invalid_password)
                }
                "-" -> {
                    APIInterface.serverErrorPopup(activity, it){
                        activity.viewModel.postLoginError.value = null
                    }
                }
                null ->{
                    binding.tilFirst.error = null
                    binding.tilSecond.error = null
                }
            }
        }

        binding.tilFirst.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton(binding)
                activity.viewModel.postLoginError.value = null
            }
        })

        binding.tilSecond.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton(binding)
                if(!binding.tilSecond.error.isNullOrEmpty()) {
                    activity.viewModel.postLoginError.value = null
                }
            }
        })
        checkInputsRefreshButton(binding)

    }

    fun twoFAHolder(binding : ContentActivityLoginBodyContentBinding, activity : LoginActivity){

        binding.buttonPrimary.text = activity.getString(R.string.login)
        binding.tilSecond.hint = activity.getString(R.string.authentication_code)
        binding.tilSecond.endIconMode = TextInputLayout.END_ICON_NONE
        binding.btnForgot.text = activity.getString(R.string.use_recovery_code)
        binding.text.text = activity.getString(R.string.please_use_2fa)
        binding.tilSecond.editText!!.transformationMethod = null
        binding.tilFirst.isVisible = false
        binding.tilSecond.editText!!.text = null
        binding.tilSecond.editText!!.inputType = InputType.TYPE_CLASS_NUMBER
        binding.tilFirst.editText!!.importantForAutofill = IMPORTANT_FOR_AUTOFILL_NO
        binding.tilSecond.editText!!.importantForAutofill = IMPORTANT_FOR_AUTOFILL_NO
        binding.buttonSecondary.text = activity.getString(R.string.back)
        binding.buttonSecondary.setOnClickListener{
            activity.binding.body.viewPager.back()
        }

        binding.buttonSecondary.setOnLongClickListener{
            activity.binding.body.viewPager.setCurrentItem(activity.binding.body.viewPager.currentItem+1, true)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return 2
    }

    class ViewHolder(var itemBinding: ContentActivityLoginBodyContentBinding) : RecyclerView.ViewHolder(itemBinding.root)
    
    
}