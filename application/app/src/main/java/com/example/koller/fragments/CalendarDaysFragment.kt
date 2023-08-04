package com.example.koller.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.koller.DayRecyclerAdapter
import com.example.shared.R
import com.example.shared.data.TodayData
import com.example.shared.recycleradapter.TodayRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CalendarDaysFragment : Fragment() {


    private lateinit var days : Array<String>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_calendar_days, container, false)


        val calendar = Calendar.getInstance()




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