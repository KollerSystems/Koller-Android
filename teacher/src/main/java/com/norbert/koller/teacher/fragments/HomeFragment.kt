package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.teacher.R

class HomeFragment : com.norbert.koller.shared.fragments.HomeFragment() {
    override fun responsibleLayout(): Int {
        return R.layout.fragment_home
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}