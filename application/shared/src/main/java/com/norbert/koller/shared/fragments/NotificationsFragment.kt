package com.norbert.koller.shared.fragments

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customview.SuperCoolRecyclerView
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.helpers.NotificationHelper
import com.norbert.koller.shared.recycleradapter.TodayRecyclerAdapter


class NotificationsFragment : Fragment() {



    private lateinit var notificsRecyclerView: SuperCoolRecyclerView
    private lateinit var notificationDataArrayList: ArrayList<TodayData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_notifications, container, false)

        notificsRecyclerView = view.findViewById(R.id.sc_recycler_view)
        notificsRecyclerView.appBar = view.findViewById(R.id.appbar_layout)

        return view
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificsRecyclerView.recyclerView.layoutManager = LinearLayoutManager(context)
        notificsRecyclerView.recyclerView.setHasFixedSize(true)

        notificationDataArrayList = arrayListOf(
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.award),"Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mivel találkoztam vele a Mondoconon"
            )
        )

        notificsRecyclerView.recyclerView.adapter = TodayRecyclerAdapter(notificationDataArrayList)

        sendNotification()

    }



    private val notificationId = 101



    private fun sendNotification() {
        // Create an explicit intent for an Activity in your app.
        val intent = Intent(requireContext(), MyApplication.openMain::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val ACTION_SNOOZE = "snooze"

        val snoozeIntent = Intent(requireContext(), MyApplication.openMain::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(requireContext(), NotificationHelper.ROOM_ORDER_CHANNEL)
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle("Bassza meg a Rare")
            .setContentText("This is a sample notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.eye, "ÚGY VAN!!", snoozePendingIntent)

        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }
}