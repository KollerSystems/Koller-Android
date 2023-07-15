package com.example.koller

import android.content.Context
import android.content.Intent
import com.example.koller.activities.LoginActivity
import com.example.koller.activities.SettingsActivity

class MyApplication : com.example.shared.MyApplication() {

    fun OpenActivity(context : Context, className : Class<*>){
        val intent = Intent(context, className)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
    }

    override fun onCreate() {
        super.onCreate()

        openSettings = {context : Context ->
            OpenActivity(context, SettingsActivity::class.java)

        }

        openLogin = {context : Context ->
            OpenActivity(context, LoginActivity::class.java)
        }
    }
}