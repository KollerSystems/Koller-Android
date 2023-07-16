package com.example.koller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.koller.activities.LoginActivity
import com.example.koller.activities.MainActivity
import com.example.koller.activities.SettingsActivity
import com.example.koller.fragments.bottomsheet.ProfileBottomSheet

class MyApplication : com.example.shared.MyApplication() {

    override fun onCreate() {
        super.onCreate()

        openSettings = {context : Context ->
            OpenActivity(context, SettingsActivity::class.java)
        }

        openLogin = {context : Context ->
            OpenActivity(context, LoginActivity::class.java)
        }

        openMain = {context : Context ->
            OpenActivity(context, MainActivity::class.java)
        }

        openProfile = {context: Context ->
            val dialog = ProfileBottomSheet()
            dialog.show((context as AppCompatActivity).supportFragmentManager, com.example.shared.fragments.bottomsheet.ProfileBottomSheet.TAG)
        }
    }
}