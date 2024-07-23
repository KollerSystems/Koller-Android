package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.norbert.koller.shared.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.ApplicationManager

class UserOutgoingsFragment(val userData : UserData? = null) : PagedFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = UserOutgoingViewPagerAdapter(userData, childFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabs, binding.viewPager){tab,position->
            when(position){
                0->{
                    tab.text = requireContext().getString(R.string.temporary)
                }
                1->{
                    tab.text = requireContext().getString(R.string.continuous)
                }
            }
        }.attach()
    }
}

class UserOutgoingViewPagerAdapter(val userData: UserData?, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle)
{
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                ApplicationManager.userOutgoingTemporaryFragment(userData)
            }
            1->{
                ApplicationManager.userOutgoingPermanentFragment(userData)
            }
            else->{
                Fragment()
            }
        }
    }

}