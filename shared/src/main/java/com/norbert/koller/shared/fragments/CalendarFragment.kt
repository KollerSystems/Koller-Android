package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.norbert.koller.shared.R
import com.google.android.material.tabs.TabLayout

abstract class CalendarFragment : PagedFragment() {

    override fun getFragmentTitle(): String? {
        return getString(R.string.calendar)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}