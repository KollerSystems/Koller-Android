package com.example.teacher.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.data.EventsData
import com.example.shared.data.RoomData
import com.example.shared.data.RoomOrderConditionsBase
import com.example.shared.data.RoomOrderData
import com.example.shared.recycleradapter.EventsRecyclerAdapter
import com.example.teacher.R
import com.example.teacher.recycleradapter.RoomRateRecyclerAdapter
import java.util.Date

class RoomRateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView : RecyclerView = view.findViewById(com.example.shared.R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val data : ArrayList<RoomOrderConditionsBase> = RoomOrderData.instance[0].Conditions


        recyclerView.adapter = RoomRateRecyclerAdapter(data)

        val lyContent : LinearLayout = view.findViewById(R.id.ly_content)

        val lyFooter : LinearLayout = view.findViewById(R.id.ly_fixed_footer)

        lyContent.post{
            lyContent.setPadding(0,0,0,lyFooter.height)
        }


    }
}