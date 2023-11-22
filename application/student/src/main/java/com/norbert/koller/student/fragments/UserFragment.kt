package com.norbert.koller.student.fragments

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.app.Person
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID
import androidx.core.app.NotificationManagerCompat
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.MyNotificationPublisher
import com.norbert.koller.student.R
import com.norbert.koller.student.helpers.NotificationHelper
import com.norbert.koller.shared.R as Rs


class UserFragment(UID : Int) : com.norbert.koller.shared.fragments.UserFragment(UID) {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendNotification()
    }



    private val notificationId = 101
    private fun sendNotification() {

        // Create an explicit intent for an Activity in your app.
        val intent = Intent(requireContext(), MyNotificationPublisher::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val ACTION_SNOOZE = "snooze"

        val snoozeIntent = Intent(requireContext(), MyApplication.openMain::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(requireContext(), NotificationHelper.ROOM_ORDER_CHANNEL)
            .setSmallIcon(Rs.drawable.notifications)
            .setContentTitle("Hello")
            .setContentText("This is a sample notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(Rs.drawable.eye, "ÚGY VAN!!", snoozePendingIntent)



        //with(NotificationManagerCompat.from(requireContext())) {
        //    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
        //        notify(notificationId, builder.build())
        //    }
        //}

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Create a new call, setting the user as the caller.
            val icon = Icon.createWithResource(requireContext(), com.norbert.koller.shared.R.drawable.bark)
            val caller = Person.Builder()
                // Caller icon
                .setIcon(icon)
                // Caller name
                .setName("Barkóczi István")
                .setImportant(true)
                .build()



            // Creating the call notification style
            val notificationStyle = Notification.CallStyle.forIncomingCall(caller, pendingIntent, pendingIntent)
            val not = Notification.Builder(requireContext(), NotificationHelper.ARRIVAL_REMINDER_CHANNEL)
                .setSmallIcon(com.norbert.koller.shared.R.drawable.phone)
                .setContentText("Incoming call")
                .setStyle(notificationStyle)
                // Intent that will be called for when tapping on the notification
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, true)
                .setOngoing(true)
                // notification category that describes this Notification. May be used by the system for ranking and filtering
                .setCategory(Notification.CATEGORY_CALL)
                .build()

            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val r = RingtoneManager.getRingtone(requireContext(), notification)
            r.play()


            with(NotificationManagerCompat.from(requireContext())) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

                    notify(notificationId, notificationStyle.build())
                }
            }

        }



    }
}