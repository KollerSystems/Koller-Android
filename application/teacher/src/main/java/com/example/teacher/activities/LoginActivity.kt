package com.example.teacher.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.teacher.R
import com.example.shared.R as Rs

class LoginActivity : com.example.shared.activities.LoginActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inplID.isCounterEnabled = false
        inplID.isHelperTextEnabled = false

        textIDNotForEveryone = R.string.this_app_is_not_for_everyone_desc

    }


}