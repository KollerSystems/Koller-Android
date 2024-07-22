package com.norbert.koller.student.pageradapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.student.fragments.*

class RegisterPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 8
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                RegisterStartFragment()
            }
            1->{
                RegisterInstitutionFragment()
            }
            2->{
                RegisterStudentFragment()
            }
            3->{
                RegisterCaretakerFragment()
            }
            4->{
                RegisterCloseRelativeFragment()
            }
            5->{
                RegisterSchoolFragment()
            }
            6->{
                RegisterSummaryFragment()
            }
            7->{
                RegisterFinishFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}