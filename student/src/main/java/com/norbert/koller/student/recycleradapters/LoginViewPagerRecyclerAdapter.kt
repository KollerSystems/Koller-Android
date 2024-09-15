package com.norbert.koller.student.recycleradapters

import android.content.Intent
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.shared.databinding.ContentActivityLoginBodyContentBinding
import com.norbert.koller.shared.recycleradapters.LoginViewPagerRecyclerAdapter
import com.norbert.koller.student.activities.RegisterActivity

class LoginViewPagerRecyclerAdapter : LoginViewPagerRecyclerAdapter() {

    override fun passwordHolder(
        binding: ContentActivityLoginBodyContentBinding,
        activity: LoginActivity
    ) {
        binding.tilFirst.editText!!.setText("tweinek5")
        binding.tilSecond.editText!!.setText("porcica1")

        super.passwordHolder(binding, activity)

        binding.tilFirst.isCounterEnabled = true
        binding.tilFirst.isHelperTextEnabled = true
        binding.tilFirst.counterMaxLength = 11
        binding.tilFirst.helperText = activity.getString(R.string.education_ID_hint)
        binding.tilFirst.hint = activity.getString(R.string.education_id)

        binding.buttonSecondary.text = activity.getString(R.string.sign_up)

        binding.buttonSecondary.setOnClickListener {
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }

}