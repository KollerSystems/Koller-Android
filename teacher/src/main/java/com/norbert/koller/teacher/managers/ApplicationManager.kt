package com.norbert.koller.teacher.managers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.norbert.koller.teacher.fragments.KeyAccessListFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.NotificationMakingManager
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.*
import com.norbert.koller.teacher.fragments.CalendarFragment
import com.norbert.koller.teacher.fragments.HomeFragment
import com.norbert.koller.teacher.fragments.RoomFragment
import com.norbert.koller.teacher.fragments.RoomListFragment
import com.norbert.koller.teacher.fragments.StudentHostelFragment
import com.norbert.koller.teacher.fragments.UserFragment
import com.norbert.koller.teacher.fragments.OutgoingListFragment
import com.norbert.koller.teacher.fragments.RoomTidinessListFragment
import com.norbert.koller.teacher.fragments.UserListFragment
import com.norbert.koller.teacher.fragments.bottomsheet.ProfileBsdFragment

class ApplicationManager : ApplicationManager() {

    override fun onCreate() {
        super.onCreate()
        NotificationMakingManager().createNotificationChannels(this)


        openSettings = {context : Context ->
            openActivity(context, SettingsActivity::class.java)
        }

        openLogin = {context : Context ->
            openActivity(context, com.norbert.koller.teacher.activities.LoginActivity::class.java)
        }

        openMain = {context : Context ->
            openActivity(context, MainActivity::class.java)
        }

        openProfile = {context: Context ->
            val dialog = ProfileBsdFragment()
            dialog.show((context as AppCompatActivity).supportFragmentManager, com.norbert.koller.shared.fragments.bottomsheet.ProfileBsdFragment.TAG)
        }

        getAppColor = {context->
            ContextCompat.getColor(context, R.color.appIconColor)
        }

        homeFragment = { HomeFragment() }
        calendarFragment = { CalendarFragment() }
        studentHostelFragment = { StudentHostelFragment() }
        roomFragment = { RoomFragment() }
        userFragment = { UserFragment() }

        roomListFragment = {RoomListFragment()}
        userListFragment = {UserListFragment()}

        outgoingListFragment = { OutgoingListFragment() }

        roomTidinessListFragment = { RoomTidinessListFragment() }

        keyAccessListFragment = { KeyAccessListFragment() }

        studyGroupsFragment = { com.norbert.koller.teacher.fragments.StudyGroupTypeListFragment() }
    }


}