package com.norbert.koller.teacher.activities

import android.os.Bundle
import com.norbert.koller.teacher.R

class LoginActivity : com.norbert.koller.shared.activities.LoginActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inplID.isCounterEnabled = false
        inplID.isHelperTextEnabled = false

        textIDNotForEveryone = R.string.this_app_is_not_for_everyone_desc

    }


}