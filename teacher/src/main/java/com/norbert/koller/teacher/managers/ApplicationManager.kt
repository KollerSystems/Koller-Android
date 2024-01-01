package com.norbert.koller.teacher.managers

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.norbert.koller.shared.fragments.NotificationsFragment
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.managers.NotificationMakingManager
import com.norbert.koller.teacher.BuildConfig
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.*
import com.norbert.koller.teacher.fragments.CalendarFragment
import com.norbert.koller.teacher.fragments.HomeFragment
import com.norbert.koller.teacher.fragments.RoomFragment
import com.norbert.koller.teacher.fragments.RoomsFragment
import com.norbert.koller.teacher.fragments.StudentHostelFragment
import com.norbert.koller.teacher.fragments.UserFragment
import com.norbert.koller.teacher.fragments.UserOutgoingPermanentFragment
import com.norbert.koller.teacher.fragments.UserOutgoingTemporaryFragment
import com.norbert.koller.teacher.fragments.UsersFragment
import com.norbert.koller.teacher.fragments.bottomsheet.ProfileFragment

class ApplicationManager : MyApplication() {

    override fun onCreate() {
        super.onCreate()
        NotificationMakingManager().createNotificationChannels(this)

        version = BuildConfig.VERSION_NAME

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
            val dialog = ProfileFragment()
            dialog.show((context as AppCompatActivity).supportFragmentManager, com.norbert.koller.shared.fragments.bottomsheet.ProfileFragment.TAG)
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
        roomsFragment = {RoomsFragment()}
        usersFragment = {UsersFragment()}

        userOutgoingTemporaryFragment = { UserOutgoingTemporaryFragment() }
        userOutgoingPermanentFragment = { UserOutgoingPermanentFragment() }
    }


}