package com.example.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.data.CrossingData
import com.example.shared.recycleradapter.GateRecyclerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

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
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-01 07:00:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-01 09:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-01 11:00:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-01 15:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-01 18:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-02 08:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-02 10:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-02 14:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-02 19:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-03 06:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-03 09:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-03 10:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-03 12:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-03 16:00:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-03 18:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-04 07:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-04 10:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-04 13:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-04 16:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-05 07:00:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-05 09:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-05 11:00:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-05 15:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-05 18:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-06 08:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-06 10:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-06 14:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-06 19:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-07 06:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-07 09:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-07 10:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-07 12:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-07 16:00:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-07 18:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-08 07:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-08 10:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-08 13:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-08 16:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-09 07:00:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-09 09:30:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-09 11:00:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-09 15:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-09 18:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-10 08:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-10 10:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-10 14:00:00"), 1),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-10 19:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-11 06:30:00"), 0),
            CrossingData(-1, -1, Timestamp.valueOf("2023-01-11 09:00:00"), 1)
        )

        var oldCrossing : CrossingData? = null

        for (i in crossingDataArrayList.indices.reversed()) {
            val currentData = crossingDataArrayList[i]

            if(currentData is String){
                crossingDataArrayList.removeAt(i)
            }
            else{
                currentData as CrossingData

                if(oldCrossing != null){

                    var instant = Instant.ofEpochMilli(oldCrossing.Time.time)
                    val oldLocalDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate()

                    instant = Instant.ofEpochMilli(currentData.Time.time)
                    val currentLocalDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate()

                    if(!oldLocalDate.isEqual(currentLocalDate)){
                        crossingDataArrayList.add(i+1, MyApplication.simpleLocalMonthDay.format(oldCrossing.Time).capitalize())
                    }
                }


                oldCrossing = currentData
            }

        }

        crossingDataArrayList.add(0, MyApplication.simpleLocalMonthDay.format((crossingDataArrayList[0] as CrossingData).Time).capitalize())

        crossingRecyclerView.adapter = GateRecyclerAdapter(crossingDataArrayList, requireContext(), text)

        val fabScrollToTop : FloatingActionButton = view.findViewById(R.id.fab_scroll_to_top)
        fabScrollToTop.setOnClickListener{
            crossingRecyclerView.smoothScrollToPosition(0)
            view.findViewById<AppBarLayout>(R.id.appbar_layout).setExpanded(true)
        }


        return view
    }
}