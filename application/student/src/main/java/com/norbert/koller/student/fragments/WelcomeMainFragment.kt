package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.norbert.koller.student.R
import com.norbert.koller.student.activities.WelcomeActivity

class WelcomeMainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View =  inflater.inflate(R.layout.fragment_welcome_main, container, false)

        val btnStart : Button = view.findViewById(R.id.button_exit)
        val btnBack : Button = view.findViewById(R.id.welcome_main_btn_back)

        btnStart.setOnClickListener {
            val viewPager = (activity as WelcomeActivity).scrollForward()
        }

        btnBack.setOnClickListener {
            activity?.finish()
        }

        return view
    }
}