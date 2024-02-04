package com.norbert.koller.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.student.recycleradapters.DayRecyclerAdapter
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.helpers.DateTimeHelper

class CalendarDaysFragment : Fragment() {






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar_days, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val mcardDate : MaterialCardView = view.findViewById(R.id.mcard_date)
        mcardDate.setOnClickListener{
            DateTimeHelper.setupDbd(mcardDate.getChildAt(0) as TextView, DateTimeHelper.monthDayWeekdayFormat).show(parentFragmentManager,  "MATERIAL_DATE_PICKER")
        }


        val RecyclerView : RecyclerView = view.findViewById(R.id.recycler_view)
        RecyclerView.adapter = DayRecyclerAdapter()

        RecyclerView.layoutManager = LinearLayoutManager(context)
        RecyclerView.setHasFixedSize(true)



    }
}