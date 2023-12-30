package com.norbert.koller.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.student.R

class CalendarStudyGroupFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.norbert.koller.shared.R.layout.activity_launch, container, false)
    }
}