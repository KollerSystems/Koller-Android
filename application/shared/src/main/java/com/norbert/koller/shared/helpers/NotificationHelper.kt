package com.norbert.koller.shared.helpers

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R

class NotificationHelper {

    companion object{

        const val REMINDER_CHANNEL_GROUP = "reminder_channel_group"
        const val GENERAL_CHANNEL_GROUP = "general_channel_group"

        const val ARRIVAL_REMINDER_CHANNEL = "arrival_reminder_channel"
        const val OCCUPATION_REMINDER_CHANNEL = "occupation_reminder_channel"
        const val ROOM_ORDER_CHANNEL = "room_order_channel"
        const val COMMENDATION_OR_WARNING_CHANNEL = "commendation_or_warning_channel"

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


        fun createNotificationChannels(myApplication: MyApplication) {
            this.myApplication = myApplication
            notificationManager = myApplication.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannelGroup(NotificationChannelGroup(REMINDER_CHANNEL_GROUP, "Emlékeztetők"))
            notificationManager.createNotificationChannelGroup(NotificationChannelGroup(GENERAL_CHANNEL_GROUP, "Általános"))

            createNotificationChannel(
                ARRIVAL_REMINDER_CHANNEL,
                R.string.arrival_reminder,
                R.string.arrival_reminder_description,
                NotificationManager.IMPORTANCE_HIGH,
                REMINDER_CHANNEL_GROUP)

            createNotificationChannel(
                OCCUPATION_REMINDER_CHANNEL,
                R.string.occupation_reminder,
                R.string.occupation_reminder_description,
                NotificationManager.IMPORTANCE_DEFAULT,
                REMINDER_CHANNEL_GROUP)

            createNotificationChannel(
                ROOM_ORDER_CHANNEL,
                R.string.room_order,
                NotificationManager.IMPORTANCE_DEFAULT,
                GENERAL_CHANNEL_GROUP)

            createNotificationChannel(
                COMMENDATION_OR_WARNING_CHANNEL,
                R.string.commendation_warning,
                NotificationManager.IMPORTANCE_DEFAULT,
                GENERAL_CHANNEL_GROUP)


        }
    }

}