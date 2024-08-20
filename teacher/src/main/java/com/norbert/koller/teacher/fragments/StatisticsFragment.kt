package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.shared.fragments.FragmentInMainActivity

class StatisticsFragment : FragmentInMainActivity() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun getFragmentTitle(): String {
        return getString(Rs.string.statistics)
    }
}