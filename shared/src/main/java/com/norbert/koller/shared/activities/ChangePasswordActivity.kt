package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentActivityChangePasswordBinding

class ChangePasswordActivity : ManageActivity() {

    lateinit var contentBinding : ContentActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayButton(getString(R.string.modify), R.drawable.save)
    }

    override fun createContentView(): ViewGroup {
        contentBinding = ContentActivityChangePasswordBinding.inflate(layoutInflater)
        return contentBinding.root
    }

    override fun getName(): String {
        return getString(R.string.change_password)
    }
}