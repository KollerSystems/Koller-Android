package com.norbert.koller.student.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.student.activities.MainActivity
import com.norbert.koller.student.R

class WelcomeSemiDoneFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_welcome_semi_done, container, false)

        val btnEnter : Button = view.findViewById(R.id.welcome_semi_done_btn_enter)

        btnEnter.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
        }

        return view
    }
}