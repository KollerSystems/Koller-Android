package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.viewmodels.DetailsViewModel


class UserOutgoingsFragment() : PagedFragment() {

    lateinit var viewModel : DetailsViewModel

    override fun getFragmentTitleAndDescription(): Pair<String?, String?> {
        return if(viewModel.ownerUID == CacheManager.userData!!.uid){
            Pair(getString(R.string.user_outgoings),"")
        } else{
            Pair(
                (CacheManager.detailsDataMap[Pair(UserData::class.java.simpleName, viewModel.ownerUID)] as UserData).name,
                getString(R.string.user_outgoings)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        if (arguments != null) {
            viewModel.ownerUID = requireArguments().getInt("id", -1)
        }
        if(viewModel.ownerUID == -1){
            viewModel.ownerUID = CacheManager.userData!!.uid
        }
        super.onViewCreated(view, savedInstanceState)



        val adapter = UserOutgoingViewPagerAdapter(viewModel.ownerUID, childFragmentManager, lifecycle)

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

class UserOutgoingViewPagerAdapter(val ownerUID: Int?, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle)
{
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt("id", ownerUID?:-1)

        return when(position){
            0->{
                val fragment = ApplicationManager.outgoingTemporaryListFragment()
                fragment.arguments = bundle
                return fragment
            }
            1->{
                val fragment = ApplicationManager.outgoingPermanentListFragment()
                fragment.arguments = bundle
                return fragment
            }
            else->{
                Fragment()
            }
        }
    }

}