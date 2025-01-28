package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentActivityManageKeyBinding

class ManageKeyActivityContent : ManageActivity() {

    private lateinit var binding: ContentActivityManageKeyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayButton(getString(R.string.acquire), R.drawable.key_active)
    }

    override fun createContentView(): ViewGroup {
        binding = ContentActivityManageKeyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getName(): String {
        return getString(R.string.acquire_keys)
    }
}