package com.norbert.koller.shared.managers

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.os.Build
import com.norbert.koller.shared.R


open class NotificationMakingManager {

    open fun createNotificationChannels(ApplicationManager: ApplicationManager) {
        NotificationMakingManager.ApplicationManager = ApplicationManager
        notificationManager = ApplicationManager.getSystemService(Application.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(REMINDER_CHANNEL_GROUP, "Emlékeztetők"))
        notificationManager.createNotificationChannelGroup(NotificationChannelGroup(GENERAL_CHANNEL_GROUP, "Általános"))

        createNotificationChannel(
            OCCUPATION_REMINDER_CHANNEL,
            R.string.occupation_reminder,
            R.string.occupation_reminder_description,
            NotificationManager.IMPORTANCE_HIGH,
            REMINDER_CHANNEL_GROUP
        )
    }

    companion object{

        fun isChannelEnabled(groupId : String, id : String) : Boolean{
            return isChannelGroupEnabled(groupId) && notificationManager.getNotificationChannel(id).importance != NotificationManager.IMPORTANCE_NONE
        }

        fun isChannelGroupEnabled(id : String) : Boolean{
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                !notificationManager.getNotificationChannelGroup(id).isBlocked
            } else {
                true
            }
        }

        const val REMINDER_CHANNEL_GROUP = "reminder_channel_group"
        const val GENERAL_CHANNEL_GROUP = "general_channel_group"

        const val OCCUPATION_REMINDER_CHANNEL = "occupation_reminder_channel"

        private lateinit var ApplicationManager : ApplicationManager
        lateinit var notificationManager: NotificationManager

        fun createNotificationChannel(id : String, name : Int, description : String?, importance : Int, group : String){

            val channel = NotificationChannel(
                id,
                ApplicationManager.getString(name),
                importance).apply {
                    this.description = description
                    this.group = group
                }

            notificationManager.createNotificationChannel(channel)

        }

        fun createNotificationChannel(id : String, name : Int, description : Int, importance : Int, group : String){

            createNotificationChannel(id, name, ApplicationManager.getString(description), importance, group)

        }

        fun createNotificationChannel(id : String, name : Int, importance : Int, group : String){

            createNotificationChannel(id, name, null, importance, group)

        }
    }
}