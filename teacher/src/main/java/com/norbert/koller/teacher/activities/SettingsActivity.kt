package com.norbert.koller.teacher.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.databinding.ContentActivitySettingsDeveloperBinding
import com.norbert.koller.shared.databinding.ContentActivitySettingsExternalBinding
import com.norbert.koller.teacher.databinding.ActivitySettingsBinding

class SettingsActivity : com.norbert.koller.shared.activities.SettingsActivity() {

    private lateinit var binding : ActivitySettingsBinding

    override fun getExternalBinding(): ContentActivitySettingsExternalBinding {
        return binding.external
    }

    override fun getDeveloperBinding(): ContentActivitySettingsDeveloperBinding {
        return binding.developer
    }


    override fun createContentView(): ViewGroup {
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)


    }

}