package com.norbert.koller.student.activities

import android.view.ViewGroup
import com.norbert.koller.shared.databinding.ContentActivitySettingsDeveloperBinding
import com.norbert.koller.shared.databinding.ContentActivitySettingsExternalBinding
import com.norbert.koller.student.databinding.ContentActivitySettingsBinding


class SettingsActivity : com.norbert.koller.shared.activities.SettingsActivity(){

    private lateinit var binding : ContentActivitySettingsBinding
    override fun getExternalBinding(): ContentActivitySettingsExternalBinding {
        return binding.external
    }

    override fun getDeveloperBinding(): ContentActivitySettingsDeveloperBinding {
        return binding.developer
    }

    override fun createContentView(): ViewGroup {
        binding = ContentActivitySettingsBinding.inflate(layoutInflater)
        return binding.root
    }

}