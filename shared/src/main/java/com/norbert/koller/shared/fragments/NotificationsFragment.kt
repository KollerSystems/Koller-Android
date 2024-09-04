package com.norbert.koller.shared.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.koller.shared.broadcastreceivers.MyNotificationPublisher
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.SuperCoolRecyclerView
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.recycleradapters.ObsRecyclerAdapter


class NotificationsFragment : FragmentInMainActivity() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.obs_fragment_notifications, container, false)
    }



    override fun getFragmentTitle(): String {
        return getString(R.string.notifications)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        sendNotification()



    }



    private fun sendNotification() {

        val intent = Intent(requireContext(), MyNotificationPublisher::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            pendingIntent
        )








    }


}