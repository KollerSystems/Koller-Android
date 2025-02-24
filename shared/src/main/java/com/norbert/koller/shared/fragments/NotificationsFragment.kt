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
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.koller.shared.broadcastreceivers.MyNotificationPublisher
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.customviews.SuperCoolRecyclerView
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.NotificationRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ObsRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator


class NotificationsFragment : ListFragment() {

    override fun getFragmentTitle(): String {
        return getString(R.string.notifications)
    }

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectUser()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return NotificationRecyclerAdapter()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendNotification()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()
        setupSort(R.string.newest, R.string.oldest,"Name")
        addSortingChip("type", R.string.type_, arrayListOf(
            ListToggleItem(getString(R.string.room_order), null, AppCompatResources.getDrawable(requireContext(), R.drawable.room), 1),
            ListToggleItem(getString(R.string.commendation_warning), null, AppCompatResources.getDrawable(requireContext(), R.drawable.award), 0)
        ))
        addDateChip()
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