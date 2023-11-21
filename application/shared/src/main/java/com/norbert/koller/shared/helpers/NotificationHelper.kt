package com.norbert.koller.shared.helpers

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R

open class NotificationHelper {


    open fun createNotificationChannels(myApplication: MyApplication) {
        NotificationHelper.myApplication = myApplication
        notificationManager = myApplication.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(REMINDER_CHANNEL_GROUP, "Emlékeztetők"))
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(GENERAL_CHANNEL_GROUP, "Általános"))



        createNotificationChannel(
            OCCUPATION_REMINDER_CHANNEL,
            R.string.occupation_reminder,
            R.string.occupation_reminder_description,
            NotificationManager.IMPORTANCE_HIGH,
            REMINDER_CHANNEL_GROUP)





    }

    companion object{

        const val REMINDER_CHANNEL_GROUP = "reminder_channel_group"
        const val GENERAL_CHANNEL_GROUP = "general_channel_group"

        const val OCCUPATION_REMINDER_CHANNEL = "occupation_reminder_channel"


        private lateinit var myApplication : MyApplication
        private lateinit var notificationManager: NotificationManager

        fun createNotificationChannel(ID : String, name : Int, description : Int, importance : Int, group : String){

            val channel = NotificationChannel(
                ID,
                myApplication.getString(name),
                importance).apply {
                    this.description = myApplication.getString(description)
                    this.group = group
                }

            notificationManager.createNotificationChannel(channel)

        }

        fun createNotificationChannel(ID : String, name : Int, importance : Int, group : String){

            val channel = NotificationChannel(
                ID,
                myApplication.getString(name),
                importance).apply {
                this.group = group
            }

            notificationManager.createNotificationChannel(channel)

        }
    }

}