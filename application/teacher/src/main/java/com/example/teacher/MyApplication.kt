package com.example.teacher

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.teacher.activities.*
import com.example.teacher.fragments.bottomsheet.ProfileBottomSheet

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