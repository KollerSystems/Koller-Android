package com.norbert.koller.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.SuperCoolRecyclerView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.recycleradapter.TodayRecyclerAdapter

class NotificationsFragment : Fragment() {



    private lateinit var notificsRecyclerView: SuperCoolRecyclerView
    private lateinit var notificationDataArrayList: ArrayList<TodayData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_notifications, container, false)

        val mainActivity = context as MainActivity
        mainActivity.setToolbarTitle(mainActivity.getString(R.string.notifications), null)
        mainActivity.changeSelectedBottomNavigationIcon(R.id.notifications)

        notificsRecyclerView = view.findViewById(R.id.sc_recycler_view)
        notificsRecyclerView.appBar = view.findViewById(R.id.appbar_layout)
        notificsRecyclerView.recyclerView.layoutManager = LinearLayoutManager(context)
        notificsRecyclerView.recyclerView.setHasFixedSize(true)

        notificationDataArrayList = arrayListOf(
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.award),"Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mivel találkoztam vele a Mondoconon"
            )
        )

        notificsRecyclerView.recyclerView.adapter = TodayRecyclerAdapter(notificationDataArrayList, requireContext())

        return view
    }


}