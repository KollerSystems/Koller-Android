package com.norbert.koller.shared.fragments

import android.content.Context
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
import com.norbert.koller.shared.activities.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.MyApplication

class UserOutgoingsFragment : Fragment() {
    companion object {
        var toGet : Int = -1

        fun open(context : Context, RID : Int){
            toGet = RID

            context as MainActivity

            context.changeFragment(UserOutgoingsFragment())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_user_outgoing, container, false)

        val tabs : TabLayout = view.findViewById(R.id.user_outgoing_tabs)
        val viewPager : ViewPager2 = view.findViewById(R.id.user_outgoing_viewpager)

        val adapter = UserOutgoingViewPagerAdapter(childFragmentManager, lifecycle)

        viewPager.adapter = adapter


        TabLayoutMediator(tabs, viewPager){tab,position->
            when(position){
                0->{
                    tab.text = requireContext().getString(R.string.temporary)
                }
                1->{
                    tab.text = requireContext().getString(R.string.continuous)
                }
            }
        }.attach()

        return view
    }
}

class UserOutgoingViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle)
{
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                MyApplication.userOutgoingTemporaryFragment()
            }
            1->{
                MyApplication.userOutgoingPermanentFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}