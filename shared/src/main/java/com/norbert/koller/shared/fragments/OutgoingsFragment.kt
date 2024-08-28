package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.norbert.koller.shared.R
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.viewmodels.DetailsViewModel

class UserOutgoingsFragment(val userData : UserData? = null) : PagedFragment() {

    lateinit var viewModel : DetailsViewModel

    override fun getFragmentTitleAndDescription(): Pair<String?, String?> {
        return if(viewModel.owner!!.uid == UserData.instance.uid){
            Pair(getString(R.string.user_outgoings),"")
        } else{
            Pair(viewModel.owner!!.name, getString(R.string.user_outgoings))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        if(userData != null){
            viewModel.owner = userData
        }
        super.onViewCreated(view, savedInstanceState)



        val adapter = UserOutgoingViewPagerAdapter(viewModel.owner, childFragmentManager, lifecycle)

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
                ApplicationManager.outgoingTemporaryFragment(userData)
            }
            1->{
                ApplicationManager.outgoingPermanentFragment(userData)
            }
            else->{
                Fragment()
            }
        }
    }

}