package com.norbert.koller.teacher.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.teacher.R

class LoginActivity : com.norbert.koller.shared.activities.LoginActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inplID.isCounterEnabled = false
        inplID.isHelperTextEnabled = false

        buttonNoAccount.setOnClickListener{

            MaterialAlertDialogBuilder(this@LoginActivity)
                .setTitle(getString(com.norbert.koller.shared.R.string.this_app_is_not_for_everyone))
                .setMessage(getString(R.string.this_app_is_not_for_everyone_desc))
                .setPositiveButton(
                    getString(com.norbert.koller.shared.R.string.understood)
                )
                { _, _ ->

                }
                .setNeutralButton(getString(com.norbert.koller.shared.R.string.open_emails))
                { _, _ ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this.startActivity(intent)
                }
                .show()
        }

    }


}