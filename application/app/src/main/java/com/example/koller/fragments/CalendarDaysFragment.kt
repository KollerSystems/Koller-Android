package com.example.koller.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.shared.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CalendarDaysFragment : Fragment() {


    private lateinit var days : Array<String>
    lateinit var viewPager : ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendar_days, container, false)

        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val dayOfWeekButNotInRetardOrder : Int = when (dayOfWeek) {
            Calendar.SUNDAY -> 7
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            else -> -1
        }



        days = arrayOf(getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday), getString(R.string.saturday), getString(R.string.sunday))

        viewPager = view.findViewById(R.id.calendar_days_view_pager)
        val adapter = CalendarDayAdapter(this, calendar, dayOfWeekButNotInRetardOrder)
        viewPager.adapter = adapter



        viewPager.setCurrentItem(dayOfWeekButNotInRetardOrder-1, false)

        val tabLayoutDays : TabLayout = view.findViewById(R.id.calendar_days_tabs)
        TabLayoutMediator(tabLayoutDays,
            viewPager,
            { tab, position -> tab.text = days[position] }).attach()





        return view
    }

}

class CalendarDayAdapter(fragment: Fragment, private val calendar: Calendar, private val dayOfWeek : Int) : FragmentStateAdapter(fragment)
{

    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        val offsetCalendar = Calendar.getInstance()
        offsetCalendar.timeInMillis = calendar.timeInMillis
        offsetCalendar.add(Calendar.DAY_OF_MONTH, (dayOfWeek - 1 - position) * -1)
        return DayFragment(offsetCalendar)
    }

}