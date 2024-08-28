package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentActivity2faCodeBinding
import com.norbert.koller.shared.managers.ApplicationManager

class TwoFACodeActivity : ManageActivity() {
    lateinit var binding : ContentActivity2faCodeBinding
    override fun createContentView(): ViewGroup {
        binding = ContentActivity2faCodeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayButton(getString(R.string.check), R.drawable.check)

        binding.chipGoogle.setOnClickListener {
            ApplicationManager.openPlayStore(this, "com.google.android.apps.authenticator2")
        }
        binding.chipMicrosoft.setOnClickListener {
            ApplicationManager.openPlayStore(this, "com.azure.authenticator")
        }
        binding.chipAegis.setOnClickListener {
            ApplicationManager.openPlayStore(this, "com.beemdevelopment.aegis")
        }

        binding.btnKey.setOnClickListener{
            ApplicationManager.setClipboard(this, binding.btnKey.text.toString())

        }
    }

    override fun getName(): String {
        return getString(R.string.enable_2fa)
    }
}