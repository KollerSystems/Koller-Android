package com.example.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.R
import com.example.shared.data.CanteenData
import com.example.shared.recycleradapter.CanteenRecyclerAdapter

class CalendarCanteenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var canteenRecyclerView: RecyclerView
    private lateinit var canteenDataArrayList: ArrayList<CanteenData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendar_canteen, container, false)

        canteenRecyclerView = view.findViewById(R.id.recycler_view)
        canteenRecyclerView.layoutManager = LinearLayoutManager(context)
        canteenRecyclerView.setHasFixedSize(true)

        canteenDataArrayList = arrayListOf(
            CanteenData("Reggeli", "6:00 - 8:45", "Száraz kenyér"),
            CanteenData("Ebéd", "13:00 - 15:45", "Zöccség leves és Polipos genyó"),
            CanteenData("Vacsora", "19:15 - 19:45", "Száraz kenyér")
        )

        canteenRecyclerView.adapter = CanteenRecyclerAdapter(canteenDataArrayList)

        return view
    }
}