package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.TutoringFragment
import com.norbert.koller.shared.fragments.CalendarCanteenFragment
import com.norbert.koller.shared.fragments.CalendarProgramsFragment
import com.google.android.material.tabs.TabLayoutMediator

class CalendarFragment : com.norbert.koller.shared.fragments.CalendarFragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CalendarViewPagerAdapter(this)

        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager){tab,position->
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
                    tab.text = getString(R.string.tutorings)
                }
                5->{
                    tab.text = getString(R.string.programs)
                }
            }
        }.attach()
    }
}

class CalendarViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)
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
                TutoringFragment()
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