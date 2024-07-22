package com.norbert.koller.teacher.pageradapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.teacher.fragments.ProgramParticipantsFragment
import com.norbert.koller.teacher.fragments.StudyGroupTypeDataFragment

class ProgramTypeViewPagerAdapter(val pid : Int? = null, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->{
                StudyGroupTypeDataFragment(pid)
            }
            1 ->{
                ProgramParticipantsFragment()
            }
            2 ->{
                ProgramParticipantsFragment()
            }
            else -> {
                Fragment()
            }
        }
    }


}