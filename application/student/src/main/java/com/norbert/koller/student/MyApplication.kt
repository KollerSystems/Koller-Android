package com.norbert.koller.student

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.norbert.koller.student.activities.LoginActivity
import com.norbert.koller.student.activities.MainActivity
import com.norbert.koller.student.activities.SettingsActivity
import com.norbert.koller.student.fragments.CalendarFragment
import com.norbert.koller.student.fragments.HomeFragment
import com.norbert.koller.student.fragments.RoomFragment
import com.norbert.koller.student.fragments.StudentHostelFragment
import com.norbert.koller.student.fragments.UserFragment
import com.norbert.koller.student.fragments.bottomsheet.ProfileBottomSheet
import com.norbert.koller.shared.fragments.NotificationsFragment

class MyApplication : com.norbert.koller.shared.MyApplication() {

    override fun onCreate() {
        super.onCreate()

        openSettings = {context : Context ->
            openActivity(context, SettingsActivity::class.java)
        }

        openLogin = {context : Context ->
            openActivity(context, LoginActivity::class.java)
        }

        openMain = {context : Context ->
            openActivity(context, MainActivity::class.java)
        }

        openProfile = {context: Context ->
            val dialog = ProfileBottomSheet()
            dialog.show((context as AppCompatActivity).supportFragmentManager, com.norbert.koller.shared.fragments.bottomsheet.ProfileBottomSheet.TAG)
        }

        homeFragment = { HomeFragment() }
        calendarFragment = { CalendarFragment() }
        studentHostelFragment = { StudentHostelFragment() }
        notificationFragment = { NotificationsFragment() }
        roomFragment = { RoomFragment() }
        userFragment = { UserFragment() }
    }
}