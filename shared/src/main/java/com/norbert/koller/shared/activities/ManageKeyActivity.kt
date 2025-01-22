package com.norbert.koller.shared.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentActivityManageKeyBinding

class ManageKeyActivityContent : AppCompatActivity() {

    private lateinit var binding: ContentActivityManageKeyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContentActivityManageKeyBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}