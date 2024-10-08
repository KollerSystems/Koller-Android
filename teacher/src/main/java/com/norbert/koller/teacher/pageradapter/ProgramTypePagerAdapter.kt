package com.norbert.koller.teacher.pageradapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.teacher.fragments.ProgramParticipantListFragment
import com.norbert.koller.teacher.fragments.StudyGroupTypeDataFragment

class ProgramTypeViewPagerAdapter(val pid : Int? = null, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 ->{
                val fragment = StudyGroupTypeDataFragment()
                val bundle = Bundle()
                bundle.putInt("id", pid?:-1)
                fragment.arguments = bundle
                return fragment
            }
            1 ->{
                ProgramParticipantListFragment()
            }
            2 ->{
                ProgramParticipantListFragment()
            }
            else -> {
                Fragment()
            }
        }
    }


}