package com.norbert.koller.student.managers

import android.app.NotificationManager
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.R

class NotificationMakingManager : com.norbert.koller.shared.managers.NotificationMakingManager() {

    override fun createNotificationChannels(myApplication: MyApplication) {

        super.createNotificationChannels(myApplication)
        createNotificationChannel(
            ARRIVAL_REMINDER_CHANNEL,
            R.string.arrival_reminder,
            R.string.arrival_reminder_description,
            NotificationManager.IMPORTANCE_MAX,
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

    companion object{

        const val ARRIVAL_REMINDER_CHANNEL = "arrival_reminder_channel"
        const val ROOM_ORDER_CHANNEL = "room_order_channel"
        const val COMMENDATION_OR_WARNING_CHANNEL = "commendation_or_warning_channel"





    }

}