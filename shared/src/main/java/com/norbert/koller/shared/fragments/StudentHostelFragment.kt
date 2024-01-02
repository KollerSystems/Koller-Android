package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity

open class StudentHostelFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_student_hostel, container, false)




        val roomsButton: Button = view.findViewById(R.id.student_hostel_button_rooms)
        val usersButton: Button = view.findViewById(R.id.student_hostel_button_users)

        roomsButton.setOnClickListener{
            (context as MainActivity).addFragment(MyApplication.roomsFragment(null))
        }

        usersButton.setOnClickListener{
            (context as MainActivity).addFragment(MyApplication.usersFragment(null))
        }

        return view
    }
}