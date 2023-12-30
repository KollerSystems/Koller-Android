package com.norbert.koller.shared

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.CountDownTimer
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.norbert.koller.shared.activities.LaunchActivity
import com.norbert.koller.shared.helpers.NotificationHelper
import java.util.concurrent.TimeUnit


class MyNotificationPublisher : BroadcastReceiver() {

    val notificationID = 1

    override fun onReceive(context: Context, intent: Intent) {

        val activityIntent = Intent(context, LaunchActivity::class.java)
        val pendingActivityIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(context, NotificationHelper.OCCUPATION_REMINDER_CHANNEL)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle("1 órád van visszaérni a kollégiumba")
            .setContentText("Nincs kimenőd, így csak 15:45-ig maradhatsz kint")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingActivityIntent)
            .setColor(MyApplication.getAppColor(context))
            .setAutoCancel(true)



        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notify(notificationID, builder.build())
            }
        }

    }


}