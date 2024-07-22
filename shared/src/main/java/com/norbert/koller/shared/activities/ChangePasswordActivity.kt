package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentChangePasswordBinding

class ChangePasswordActivity : ManageActivity() {

    lateinit var contentBinding : ContentChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getContentView(): ViewGroup {
        contentBinding = ContentChangePasswordBinding.inflate(layoutInflater)
        return contentBinding.root
    }

    override fun getName(): Int {
        return R.string.change_password
    }
}