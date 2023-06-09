package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.koller.data.CrossingData
import com.example.koller.data.UserData
import com.example.koller.recycleradapter.GateRecyclerAdapter
import com.example.koller.recycleradapter.UserPreviewRecyclerAdapter
import java.sql.Timestamp

class UserGateFragment : Fragment() {



    private lateinit var crossingRecyclerView: RecyclerView
    private lateinit var crossingDataArrayList: ArrayList<Any>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_user_gate, container, false)

        crossingRecyclerView = view.findViewById(R.id.recycler_view)
        crossingRecyclerView.layoutManager = LinearLayoutManager(context)
        crossingRecyclerView.setHasFixedSize(true)

        val text : TextView = view.findViewById(R.id.text_view_recycler_view_header)

        crossingDataArrayList = arrayListOf(
            "Július 1.",
            CrossingData(-1,-1, Timestamp(1688162400000), 0),
            CrossingData(-1,-1, Timestamp(1688162400000), 1),
            CrossingData(-1,-1, Timestamp(1688162400000), 0),
            CrossingData(-1,-1, Timestamp(1688162400000), 1),
            CrossingData(-1,-1, Timestamp(1688162400000), 0),
            "Július 2.",
            CrossingData(-1,-1, Timestamp(1688248800000), 1),
            CrossingData(-1,-1, Timestamp(1688248800000), 0),
            CrossingData(-1,-1, Timestamp(1688248800000), 1),
            CrossingData(-1,-1, Timestamp(1688248800000), 0),
            CrossingData(-1,-1, Timestamp(1688248800000), 1),
            "Július 3.",
            CrossingData(-1,-1, Timestamp(1688335200000), 0),
            CrossingData(-1,-1, Timestamp(1688335200000), 1),
            CrossingData(-1,-1, Timestamp(1688335200000), 0),
            CrossingData(-1,-1, Timestamp(1688335200000), 1),
            CrossingData(-1,-1, Timestamp(1688335200000), 0),
            "Július 4.",
            CrossingData(-1,-1, Timestamp(1688421600000), 1),
            CrossingData(-1,-1, Timestamp(1688421600000), 0),
            CrossingData(-1,-1, Timestamp(1688421600000), 1),
            CrossingData(-1,-1, Timestamp(1688421600000), 0),
            CrossingData(-1,-1, Timestamp(1688421600000), 1),
            CrossingData(-1,-1, Timestamp(1688421600000), 0),
            CrossingData(-1,-1, Timestamp(1688421600000), 1),
            CrossingData(-1,-1, Timestamp(1688421600000), 0),
            CrossingData(-1,-1, Timestamp(1688421600000), 1),
            CrossingData(-1,-1, Timestamp(1688421600000), 0),
            "Július 5.",
            CrossingData(-1,-1, Timestamp(1688508000000), 1),
            CrossingData(-1,-1, Timestamp(1688508000000), 0),
            CrossingData(-1,-1, Timestamp(1688508000000), 1),
            CrossingData(-1,-1, Timestamp(1688508000000), 0),
            CrossingData(-1,-1, Timestamp(1688508000000), 1),
            "Július 6.",
            CrossingData(-1,-1, Timestamp(1688594400000), 0),
            CrossingData(-1,-1, Timestamp(1688594400000), 1),
            CrossingData(-1,-1, Timestamp(1688594400000), 0)
        )

        crossingRecyclerView.adapter = GateRecyclerAdapter(crossingDataArrayList, requireContext(), text)

        return view
    }
}