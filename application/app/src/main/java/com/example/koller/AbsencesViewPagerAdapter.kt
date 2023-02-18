package com.example.koller

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.koller.fragments.*

class AbsencesViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                AbsencesGateFragment()
            }
            1->{
                AbsencesBaseProgramsFragment()
            }
            2->{
                AbsencesStudyGroupFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}