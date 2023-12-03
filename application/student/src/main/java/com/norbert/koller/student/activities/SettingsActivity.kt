package com.norbert.koller.student.activities

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.checkbox.MaterialCheckBox
import com.norbert.koller.shared.customview.SimpleCardButton
import com.norbert.koller.shared.customview.SimpleCardButtonWithToggle
import com.norbert.koller.student.R
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.student.helpers.NotificationHelper
import java.util.*
import com.norbert.koller.shared.helpers.NotificationHelper as SharedNotificationHelper


class SettingsActivity : com.norbert.koller.shared.activities.SettingsActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_settings)
        super.onCreate(savedInstanceState)



    }

}