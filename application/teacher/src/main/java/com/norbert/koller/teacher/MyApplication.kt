package com.norbert.koller.teacher

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.norbert.koller.teacher.BuildConfig
import com.norbert.koller.shared.fragments.NotificationsFragment
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
import com.norbert.koller.teacher.fragments.bottomsheet.ProfileBottomSheet

class MyApplication : com.norbert.koller.shared.MyApplication() {

    override fun onCreate() {
        super.onCreate()

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
            val dialog = ProfileBottomSheet()
            dialog.show((context as AppCompatActivity).supportFragmentManager, com.norbert.koller.shared.fragments.bottomsheet.ProfileBottomSheet.TAG)
        }

        homeFragment = { HomeFragment() }
        calendarFragment = { CalendarFragment() }
        studentHostelFragment = { StudentHostelFragment() }
        notificationFragment = { NotificationsFragment() }
        roomFragment = {RID : Int -> RoomFragment(RID) }
        userFragment = {UID : Int -> UserFragment(UID) }
        roomsFragment = {RoomsFragment()}
        usersFragment = {UsersFragment()}

        userOutgoingTemporaryFragment = { UserOutgoingTemporaryFragment() }
        userOutgoingPermanentFragment = { UserOutgoingPermanentFragment() }
    }


}