package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.koller.data.TodayData
import com.example.koller.recycleradapter.TodayRecyclerAdapter

class NotificationsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var notificsRecyclerView: RecyclerView
    private lateinit var notificationDataArrayList: ArrayList<TodayData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_notifications, container, false)

        notificsRecyclerView = view.findViewById(R.id.recycleview_notifications)
        notificsRecyclerView.layoutManager = LinearLayoutManager(context)
        notificsRecyclerView.setHasFixedSize(true)

        notificationDataArrayList = arrayListOf(
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.award),"Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mivel találkoztam vele a Mondoconon"
            )
        )

        notificsRecyclerView.adapter = TodayRecyclerAdapter(notificationDataArrayList, requireContext())

        return view
    }
}