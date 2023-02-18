package com.example.koller;

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.koller.fragments.*

class WelcomeViewPagerAdapterFinal(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                WelcomeMainFragment()
            }
            1->{
                WelcomeNewPasswordFragment()
            }
            2->{
                WelcomeBasicInformationsFragment()
            }
            3->{
                WelcomeInterestsFragment()
            }
            4->{
                WelcomePersonalityFragment()
            }
            5->{
                WelcomeUsersFragment()
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