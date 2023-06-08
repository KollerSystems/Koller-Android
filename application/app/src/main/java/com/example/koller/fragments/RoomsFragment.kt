package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.koller.data.TodayData
import com.example.koller.recycleradapter.RoomRecyclerAdapter

class RoomsFragment : Fragment() {

    private lateinit var roomsRecyclerView: RecyclerView
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

        roomsRecyclerView = view.findViewById(R.id.recycler_view)
        roomsRecyclerView.layoutManager = LinearLayoutManager(context)
        roomsRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData(AppCompatResources.getDrawable(requireContext(), R.drawable.bed), "254", "K. Márton, H. Károly, N. Norbert"),
            TodayData(AppCompatResources.getDrawable(requireContext(), R.drawable.bed),"255", "K. Gábor, A. Norbert"),
            TodayData(AppCompatResources.getDrawable(requireContext(), R.drawable.bed),"256", "K. Gábor, A. Norbert"),
            TodayData(AppCompatResources.getDrawable(requireContext(), R.drawable.bed),"257", "K. Gábor, A. Norbert"),
            TodayData(AppCompatResources.getDrawable(requireContext(), R.drawable.monitor),"Ügyeletes terem", "2. emelet, A oldal"),
            TodayData(AppCompatResources.getDrawable(requireContext(), R.drawable.kitchen),"Teakonyha", "2. emelet, A oldal",))

        roomsRecyclerView.adapter = RoomRecyclerAdapter(todayDataArrayList, requireContext())

        return view
    }
}