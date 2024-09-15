package com.norbert.koller.teacher.managers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.shared.fragments.NotificationsFragment
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
import com.norbert.koller.teacher.fragments.OutgoingPermanentListFragment
import com.norbert.koller.teacher.fragments.OutgoingTemporaryListFragment
import com.norbert.koller.teacher.fragments.UserListFragment
import com.norbert.koller.teacher.fragments.bottomsheet.ProfileBsdfFragment

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
        roomFragment = {rid -> RoomFragment(rid) }
        userFragment = {uid -> UserFragment(uid) }

        roomListFragment = {RoomListFragment()}
        userListFragment = {UserListFragment()}

        outgoingTemporaryListFragment = { OutgoingTemporaryListFragment(it) }
        outgoingPermanentListFragment = { OutgoingPermanentListFragment(it) }

        studyGroupsFragment = {map-> com.norbert.koller.teacher.fragments.StudyGroupTypeListFragment(map) }
    }


}