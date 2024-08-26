package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.CanteenFragment
import com.google.android.material.tabs.TabLayoutMediator

class CalendarFragment : com.norbert.koller.shared.fragments.CalendarFragment() {

    override fun getViewPager(): ViewPager2 {
        return binding.viewPager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CalendarViewPagerAdapter(this)

        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabs, binding.viewPager){tab,position->
            when(position){
                0->{
                    tab.text = getString(R.string.days)
                }
                1->{
                    tab.text = getString(R.string.duty_teachers)
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
                DaysFragment()
            }
            1->{
                Fragment()
            }
            2->{
                CanteenFragment()
            }
            3->{
                BaseProgramsFragment()
            }
            4->{
                StudyGroupsFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}