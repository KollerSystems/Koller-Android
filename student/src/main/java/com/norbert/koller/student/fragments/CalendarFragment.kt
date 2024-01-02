package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.CalendarCanteenFragment
import com.google.android.material.tabs.TabLayoutMediator

class CalendarFragment : com.norbert.koller.shared.fragments.CalendarFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CalendarViewPagerAdapter(this)

        viewPager.adapter = adapter
        viewPager.isSaveEnabled = false


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
            }
        }.attach()
    }
}

class CalendarViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)
{
    override fun getItemCount(): Int {
        return 4
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
            else->{
                Fragment()
            }
        }
    }
}