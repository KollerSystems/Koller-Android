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


class UserFragment : com.norbert.koller.shared.fragments.UserFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}