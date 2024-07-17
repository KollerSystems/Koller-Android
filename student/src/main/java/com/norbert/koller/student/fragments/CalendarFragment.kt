package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.CalendarCanteenFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.student.fragments.CalendarStudyGroupFragment

class CalendarFragment : com.norbert.koller.shared.fragments.CalendarFragment() {

    lateinit var adapter : CalendarViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CalendarViewPagerAdapter(this)
        viewPager.adapter = adapter



        TabLayoutMediator(tabs, viewPager){tab,position->
            when(position){
                0->{
                    tab.text = getString(R.string.days)
                }
                1->{
                    tab.text = "Ügyeletesek"
                }
                2->{
                    tab.text = getString(R.string.canteen)
                }
                3->{
                    tab.text = getString(R.string.base_programs)
                }
                4->{
                    tab.text = getString(R.string.study_groups)
                }
            }
        }.attach()
    }
}

class CalendarViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment)
{
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                CalendarDaysFragment()
            }
            1->{
                Fragment()
            }
            2->{
                CalendarCanteenFragment()
            }
            3->{
                CalendarBaseProgramsFragment()
            }
            4->{
                CalendarStudyGroupFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}