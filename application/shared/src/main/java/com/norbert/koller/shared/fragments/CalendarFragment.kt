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

        val mainActivity = context as MainActivity
        mainActivity.setToolbarTitle(mainActivity.getString(R.string.calendar), null)


        tabLayout = view.findViewById(R.id.calendar_tabs)
        viewPager = view.findViewById(R.id.calendar_view_pager)
    }
}