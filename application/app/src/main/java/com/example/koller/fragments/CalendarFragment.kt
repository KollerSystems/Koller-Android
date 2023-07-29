package com.example.koller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.koller.*
import com.example.shared.R
import com.example.shared.fragments.CalendarCanteenFragment
import com.example.shared.fragments.CalendarProgramsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CalendarFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendar, container, false)


        val tabs : TabLayout = view.findViewById(R.id.calendar_tabs)
        val viewPager : ViewPager2 = view.findViewById(R.id.calendar_view_pager)

        val adapter = CalendarViewPagerAdapter(childFragmentManager, lifecycle)

        viewPager.adapter = adapter


        TabLayoutMediator(tabs, viewPager){tab,position->
            when(position){
                0->{
                    tab.text = getString(R.string.days)
                }
                1->{
                    tab.text = getString(R.string.canteen)
                }
                2->{
                    tab.text = getString(R.string.base_programs)
                }
                3->{
                    tab.text = getString(R.string.study_groups)
                }
                4->{
                    tab.text = getString(R.string.tutoring)
                }
                5->{
                    tab.text = getString(R.string.programs)
                }
            }
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewPager.isUserInputEnabled = position != 0
                super.onPageSelected(position)
            }
        })

        return view;
    }

}

class CalendarViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle)
{
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                CalendarDaysFragment()
            }
            1->{
                CalendarCanteenFragment()
            }
            2->{
                CalendarBaseProgramsFragment()
            }
            3->{
                CalendarStudyGroupFragment()
            }
            4->{
                Fragment()
            }
            5->{
                CalendarProgramsFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}