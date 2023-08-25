package com.example.shared.fragments

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
import com.example.shared.R
import com.example.shared.activities.MainActivity
import com.example.shared.navigateWithDefaultAnimation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserOutgoingFragment : Fragment() {
    companion object {
        var toGet : Int = -1

        fun open(context : Context, RID : Int){
            toGet = RID

            context as MainActivity

            context.changeFragment(UserOutgoingFragment())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_user_outgoing, container, false)

        val mainActivity = context as MainActivity
        mainActivity.setToolbarTitle(mainActivity.getString(R.string.outgoings), mainActivity.getString(R.string.student_hostel))
        mainActivity.changeSelectedBottomNavigationIcon(R.id.studentHostel)

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
                UserOutgoingTemporaryFragment()
            }
            1->{
                UserOutgoingPermanentFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}