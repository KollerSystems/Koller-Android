package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.Content2faPasswordBinding
import com.norbert.koller.shared.managers.ApplicationManager

class TwoFAPasswordActivity : ManageActivity() {
    lateinit var binding : Content2faPasswordBinding
    override fun createContentView(): ViewGroup {
        binding = Content2faPasswordBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manageBarBinding.button.text = getString(R.string.next)
        manageBarBinding.button.setOnClickListener{
            finish()
            ApplicationManager.openActivity(this, TwoFACodeActivity::class.java)
        }
    }

    override fun getName(): String {
        return getString(R.string.enable_2fa)
    }
}