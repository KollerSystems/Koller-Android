package com.norbert.koller.student.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.student.WelcomeSummaryFragment

class WelcomeFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                WelcomeMainFragment()
            }
            1->{
                WelcomeSemiDoneFragment()
            }
            2->{
                WelcomeBasicInformationsFragment()
            }
            3->{
                WelcomePersonalityFragment()
            }
            4->{
                WelcomeSummaryFragment()
            }
            5->{
                WelcomeNewPasswordFragment()
            }
            6->{
                WelcomeFinalDoneFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}