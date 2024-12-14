package com.norbert.koller.teacher.recycleradapters

import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.shared.databinding.ContentActivityLoginBodyContentBinding
import com.norbert.koller.shared.recycleradapters.LoginViewPagerRecyclerAdapter
import com.norbert.koller.teacher.R

class LoginViewPagerRecyclerAdapter : LoginViewPagerRecyclerAdapter() {

    @SuppressLint("SetTextI18n")
    override fun passwordHolder(
        binding: ContentActivityLoginBodyContentBinding,
        activity: LoginActivity
    ) {
        binding.tilFirst.editText!!.setText("cmulranhu")
        binding.tilSecond.editText!!.setText("porcica1")

        super.passwordHolder(binding, activity)

        binding.tilFirst.isCounterEnabled = false
        binding.tilFirst.isHelperTextEnabled = false
        binding.tilFirst.hint = activity.getString(com.norbert.koller.shared.R.string.username)

        binding.buttonSecondary.text = activity.getString(com.norbert.koller.shared.R.string.no_account_yet)

        binding.buttonSecondary.setOnClickListener{

            MaterialAlertDialogBuilder(activity)
                .setTitle(activity.getString(com.norbert.koller.shared.R.string.this_app_is_not_for_everyone))
                .setMessage(activity.getString(R.string.this_app_is_not_for_everyone_desc))
                .setPositiveButton(
                    activity.getString(com.norbert.koller.shared.R.string.understood)
                )
                { _, _ ->

                }
                .setNeutralButton(activity.getString(com.norbert.koller.shared.R.string.open_emails))
                { _, _ ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    activity.startActivity(intent)
                }
                .show()
        }
    }
}