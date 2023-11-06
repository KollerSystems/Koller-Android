package com.norbert.koller.student.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.student.DayRecyclerAdapter
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.helpers.DateTimeHelper

class CalendarDaysFragment : Fragment() {


    private lateinit var days : Array<String>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_calendar_days, container, false)


        val mcardDate : MaterialCardView = view.findViewById(R.id.mcard_date)
        mcardDate.setOnClickListener{
            DateTimeHelper.setupDbd(mcardDate.getChildAt(0) as TextView).show(parentFragmentManager,  "MATERIAL_DATE_PICKER")
        }



        days = arrayOf(getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday), getString(R.string.saturday), getString(R.string.sunday))

        val recyclerView : RecyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val datas = arrayListOf(
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.room),"Szobarend", "K, P", "4"),
            TodayData(context?.getDrawable(R.drawable.award),"Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mivel találkoztam vele a Mondoconon"
            )
        )

        recyclerView.adapter = DayRecyclerAdapter(datas)




        return view
    }

}