package com.norbert.koller.shared.broadcastreceivers

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.LaunchActivity
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.NotificationMakingManager


class MyNotificationPublisher : BroadcastReceiver() {

    val notificationID = 1

    override fun onReceive(context: Context, intent: Intent) {

        val activityIntent = Intent(context, LaunchActivity::class.java)
        val pendingActivityIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(context, NotificationMakingManager.OCCUPATION_REMINDER_CHANNEL)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle("DUMMY 1 órád van visszaérni a kollégiumba")
            .setContentText("Nincs kimenőd, így csak 15:45-ig maradhatsz kint")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingActivityIntent)
            .setColor(ApplicationManager.getAppColor(context))
            .setAutoCancel(true)



        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notify(notificationID, builder.build())
            }
        }

    }


}