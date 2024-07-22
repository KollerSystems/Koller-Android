package com.norbert.koller.student.activities

import android.os.Bundle
import com.norbert.koller.shared.databinding.ContentActivitySettingsDeveloperBinding
import com.norbert.koller.shared.databinding.ContentActivitySettingsExternalBinding
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.ActivitySettingsBinding


class SettingsActivity : com.norbert.koller.shared.activities.SettingsActivity(){

    private lateinit var binding : ActivitySettingsBinding
    override fun getExternalBinding(): ContentActivitySettingsExternalBinding {
        return binding.external
    }

    override fun getDeveloperBinding(): ContentActivitySettingsDeveloperBinding {
        return binding.developer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

}