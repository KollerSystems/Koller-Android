package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.TooltipCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.shared.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class CalendarDaysFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var days : Array<String>
    lateinit var viewPager : ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendar_days, container, false)

        days = arrayOf(getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday), getString(R.string.saturday), getString(R.string.sunday))

        viewPager = view.findViewById(R.id.calendar_days_view_pager)
        viewPager.adapter = CalendarDayAdapter(this)

        val tabLayoutDays : TabLayout = view.findViewById(R.id.calendar_days_tabs)
        TabLayoutMediator(tabLayoutDays,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position -> tab.text = days[position] }).attach()

        // turn off that tooltip text thing immediately on activity creation
        // turn off that tooltip text thing immediately on activity creation
        for (i in 0 until tabLayoutDays.tabCount) {
            Objects.requireNonNull(tabLayoutDays.getTabAt(i))
                ?.let { TooltipCompat.setTooltipText(it.view, null) }
        }

        return view
    }

}

class CalendarDayAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)
{
    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        return DayFragment()
    }

}