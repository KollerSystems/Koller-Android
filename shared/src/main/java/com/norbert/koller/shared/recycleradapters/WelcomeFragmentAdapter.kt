package com.norbert.koller.shared.recycleradapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.shared.fragments.InformationCaretakerFragment
import com.norbert.koller.shared.fragments.InformationCloseRelativeFragment
import com.norbert.koller.shared.fragments.InformationSchoolFragment
import com.norbert.koller.shared.fragments.InformationYouFragment
import com.norbert.koller.shared.fragments.WelcomeFinalDoneFragment
import com.norbert.koller.shared.fragments.WelcomeInstitutionFragment
import com.norbert.koller.shared.fragments.WelcomeMainFragment
import com.norbert.koller.shared.fragments.WelcomeSummaryFragment

class WelcomeFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 8
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                WelcomeMainFragment()
            }
            1->{
                WelcomeInstitutionFragment()
            }
            2->{
                InformationYouFragment()
            }
            3->{
                InformationCaretakerFragment()
            }
            4->{
                InformationCloseRelativeFragment()
            }
            5->{
                InformationSchoolFragment()
            }
            6->{
                WelcomeSummaryFragment()
            }
            7->{
                WelcomeFinalDoneFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}