package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentChangePasswordBinding

class ChangePasswordActivity : ManageActivity() {

    lateinit var contentBinding : ContentChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun createContentView(): ViewGroup {
        contentBinding = ContentChangePasswordBinding.inflate(layoutInflater)
        return contentBinding.root
    }

    override fun getName(): String {
        return getString(R.string.change_password)
    }
}