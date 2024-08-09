package com.norbert.koller.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CanteenData
import com.norbert.koller.shared.recycleradapters.CanteenRecyclerAdapter
import java.util.Date

class CanteenFragment : FragmentInMainActivity() {

    override fun getFragmentTitle(): String? {
        return null
    }

    private lateinit var canteenRecyclerView: RecyclerView
    private lateinit var canteenDataArrayList: ArrayList<CanteenData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_canteen, container, false)

        canteenRecyclerView = view.findViewById(R.id.recycler_view)
        canteenRecyclerView.layoutManager = LinearLayoutManager(context)
        canteenRecyclerView.setHasFixedSize(true)

        canteenDataArrayList = arrayListOf(
            CanteenData(1,"Reggeli", "6:00 - 8:45", "Száraz kenyér", Date()),
            CanteenData(1,"Ebéd", "13:00 - 15:45", "Zöccség leves és Polipos genyó", Date()),
            CanteenData(1,"Vacsora", "19:15 - 19:45", "Száraz kenyér", Date())
        )

        canteenRecyclerView.adapter = CanteenRecyclerAdapter(canteenDataArrayList)

        return view
    }
}