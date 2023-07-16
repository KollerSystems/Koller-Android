package com.example.teacher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teacher.R

class HomeFragment : com.example.shared.fragments.HomeFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        realView = inflater.inflate(R.layout.fragment_home, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        return realView
    }
}