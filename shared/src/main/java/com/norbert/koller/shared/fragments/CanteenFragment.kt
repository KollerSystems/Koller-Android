package com.norbert.koller.shared.fragments

import android.os.Bundle
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
        val view : View = inflater.inflate(R.layout.obs_fragment_canteen, container, false)

        canteenRecyclerView = view.findViewById(R.id.recycler_view)
        canteenRecyclerView.layoutManager = LinearLayoutManager(context)
        canteenRecyclerView.setHasFixedSize(true)

        canteenDataArrayList = arrayListOf(
            CanteenData(1,0, "6:00 - 8:45", getString(R.string.fake_breakfast_1), Date()),
            CanteenData(1,1, "13:00 - 15:45", getString(R.string.fake_lunch_1), Date()),
            CanteenData(1,2, "19:15 - 19:45", getString(R.string.fake_dinner_1), Date())
        )

        canteenRecyclerView.adapter = CanteenRecyclerAdapter(canteenDataArrayList)

        return view
    }


}