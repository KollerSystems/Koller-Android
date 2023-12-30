package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.recycleradapters.TodayRecyclerAdapter
import java.util.ArrayList

abstract class HomeFragment : Fragment() {

    private lateinit var todayRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>
    private lateinit var unreadRecyclerView: RecyclerView
    private lateinit var unreadDataArrayList: ArrayList<TodayData>
    lateinit var refresh : SwipeRefreshLayout





    var time : Float = 0.0f
    

    lateinit var outgoingTimer : CountDownTimer
    var outgoingTimerRunning = false
    lateinit var lessonTimer : CountDownTimer
    var lessonsTimerRunning = false

    abstract fun responsibleLayout() : Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        refresh = view.findViewById(R.id.home_refresh)


        todayRecyclerView = view.findViewById(R.id.todayRecyclerView)
        todayRecyclerView.layoutManager = LinearLayoutManager(context)
        todayRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData(false, context?.getDrawable(R.drawable.room), getString(R.string.room_order), "K, P", "4,"),
            TodayData(false, context?.getDrawable(R.drawable.award),"Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mert miért ne."
            )
        )

        val adapter = TodayRecyclerAdapter(todayDataArrayList)
        todayRecyclerView.adapter = adapter





        unreadRecyclerView = view.findViewById(R.id.home_recycle_view_unread)
        unreadRecyclerView.layoutManager = LinearLayoutManager(context)
        unreadRecyclerView.setHasFixedSize(true)

        unreadDataArrayList = arrayListOf(
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "5"),
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "4"),
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
            R.string.perfect), "5"),
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
            R.string.perfect), "4"),
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(R.string.perfect), "4"),
        )

        unreadRecyclerView.adapter = TodayRecyclerAdapter(unreadDataArrayList)

        val fabRoot: View = view.findViewById(R.id.home_fab_root)
    }
}