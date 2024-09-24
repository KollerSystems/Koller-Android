package com.norbert.koller.student.managers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.student.activities.MainActivity
import com.norbert.koller.student.activities.SettingsActivity
import com.norbert.koller.student.fragments.CalendarFragment
import com.norbert.koller.student.fragments.HomeFragment
import com.norbert.koller.student.fragments.RoomFragment
import com.norbert.koller.student.fragments.StudentHostelFragment
import com.norbert.koller.student.fragments.UserFragment
import com.norbert.koller.student.fragments.bottomsheet.ProfileBsdfFragment
import com.norbert.koller.shared.fragments.NotificationsFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.student.R

class ApplicationManager : ApplicationManager() {

    init{

    }

    override fun onCreate() {


        super.onCreate()
        NotificationMakingManager().createNotificationChannels(this)


        openSettings = {context : Context ->
            openActivity(context, SettingsActivity::class.java)
        }

        openLogin = {context : Context ->
            openActivity(context, com.norbert.koller.student.activities.LoginActivity::class.java)
        }

        openMain = {context : Context ->
            openActivity(context, MainActivity::class.java)
        }

        openProfile = {context: Context ->
            val dialog = ProfileBsdfFragment()
            dialog.show((context as AppCompatActivity).supportFragmentManager, com.norbert.koller.shared.fragments.bottomsheet.ProfileBsdfFragment.TAG)
        }

        getAppColor = {context->
            ContextCompat.getColor(context, R.color.app_icon_color)
        }

        homeFragment = { HomeFragment() }
        calendarFragment = { CalendarFragment() }
        studentHostelFragment = { StudentHostelFragment() }
        notificationFragment = { NotificationsFragment() }
        roomFragment = { RoomFragment() }
        userFragment = { UserFragment() }

        studyGroupsFragment = { com.norbert.koller.student.fragments.StudyGroupTypeListFragment() }
    }
}