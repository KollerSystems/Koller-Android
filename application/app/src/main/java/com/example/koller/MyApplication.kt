package com.example.koller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.shared.R as Rs
import com.example.koller.R
import com.example.koller.activities.LoginActivity
import com.example.koller.activities.MainActivity
import com.example.koller.activities.SettingsActivity
import com.example.koller.fragments.CalendarFragment
import com.example.koller.fragments.HomeFragment
import com.example.koller.fragments.StudentHostelFragment
import com.example.koller.fragments.bottomsheet.ProfileBottomSheet
import com.example.shared.fragments.NotificationsFragment

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

        homeFragment = { HomeFragment() }
        calendarFragment = { CalendarFragment() }
        studentHostelFragment = { StudentHostelFragment() }
        notificationFragment = { NotificationsFragment() }
    }
}