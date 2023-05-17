package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.koller.data.TodayData

class RoomsFragment : Fragment() {

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_rooms, container, false)

        /*usersRecyclerView = view.findViewById(R.id.recycler_view_rooms)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        usersRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData("Norbert", "254", "🐔"),
            TodayData("Norbert", "255", "🥶"),
            TodayData("Norbert", "256", "🤓"),
            TodayData("Norbert", "257", "🦭"),
            TodayData("Norbert", "258", "🥵"),
            TodayData("Norbert", "259", "😛"),
            TodayData("Norbert", "260", "😶‍🌫️"),
            TodayData("Norbert", "261", "😵"),
            TodayData("Norbert", "262", "👼"),
            TodayData("Norbert", "263", "🐷"),
            TodayData("Norbert", "264", "❤️"),
            TodayData("Norbert", "265", "💀"),
            TodayData("Norbert", "266", "👺"),
            TodayData("Norbert", "267", "🤡"),
            TodayData("Norbert", "268", "🦄"),
            TodayData("Norbert", "269", "♋"))

        usersRecyclerView.adapter = TodayRecyclerAdapter(todayDataArrayList)*/

        return view
    }
}