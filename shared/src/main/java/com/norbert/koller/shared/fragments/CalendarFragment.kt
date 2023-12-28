package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.google.android.material.tabs.TabLayout

abstract class CalendarFragment : Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        tabLayout = view.findViewById(R.id.calendar_tabs)
        viewPager = view.findViewById(R.id.calendar_view_pager)


        val mainActivity = context as MainActivity

        /*mainActivity.bottomNavigationView.setOnItemReselectedListener {item ->

            if(item.itemId == R.id.calendar){
                tabLayout.getTabAt(0)!!.select()
            }

        }*/

    }
}