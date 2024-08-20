package com.norbert.koller.shared.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.recycleradapters.ObsRecyclerAdapter
import java.util.ArrayList

abstract class HomeFragment : FragmentInMainActivity() {

    private lateinit var todayRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>
    private lateinit var weekRecyclerView: RecyclerView
    private lateinit var weekDataArrayList: ArrayList<TodayData>
    private lateinit var unreadRecyclerView: RecyclerView
    private lateinit var unreadDataArrayList: ArrayList<TodayData>
    lateinit var refresh : SwipeRefreshLayout





    var time : Float = 0.0f
    

    lateinit var outgoingTimer : CountDownTimer
    var outgoingTimerRunning = false
    lateinit var lessonTimer : CountDownTimer
    var lessonsTimerRunning = false

    abstract fun responsibleLayout() : Int

    override fun getFragmentTitle(): String? {
        return getString(R.string.home)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        refresh = view.findViewById(R.id.home_refresh)


        todayRecyclerView = view.findViewById(R.id.todayRecyclerView)
        todayRecyclerView.layoutManager = LinearLayoutManager(context)
        todayRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData(false, context?.getDrawable(R.drawable.wave),"Kimenő", "Bevásárlás • 16:00 - 18:00"),
            TodayData(true, context?.getDrawable(R.drawable.room), getString(R.string.room_order), "K, P", "4"),
            TodayData(true, context?.getDrawable(R.drawable.award),"Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mert miért ne."
            )
        )

        val adapter = ObsRecyclerAdapter(todayDataArrayList)
        todayRecyclerView.adapter = adapter





        unreadRecyclerView = view.findViewById(R.id.home_recycle_view_unread)
        unreadRecyclerView.layoutManager = LinearLayoutManager(context)
        unreadRecyclerView.setHasFixedSize(true)

        unreadDataArrayList = arrayListOf(
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "5"),
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "4")
        )

        unreadRecyclerView.adapter = ObsRecyclerAdapter(unreadDataArrayList)


        weekRecyclerView = view.findViewById(R.id.todayRecyclerViewWeek)
        weekRecyclerView.layoutManager = LinearLayoutManager(context)
        weekRecyclerView.setHasFixedSize(true)

        weekDataArrayList = arrayListOf(
            TodayData(true, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "5"),
            TodayData(true, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "4"),
            TodayData(true, context?.getDrawable(R.drawable.rocket),"Szakkör", "Online Marketing • Megjelent", AppCompatResources.getDrawable(requireContext(), R.drawable.check_circle)),
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "5"),
            TodayData(true, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "4"),
        )

        weekRecyclerView.adapter = ObsRecyclerAdapter(weekDataArrayList)
    }
}