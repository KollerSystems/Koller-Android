package com.norbert.koller.shared.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.norbert.koller.shared.R

class ChangePasswordActivity : ManageActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun getContentLayout(): Int {
        return R.layout.activity_change_password
    }

    override fun getName(): Int {
        return R.string.change_password
    }
}