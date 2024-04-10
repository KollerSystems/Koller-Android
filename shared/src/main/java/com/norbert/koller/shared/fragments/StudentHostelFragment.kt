package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.norbert.koller.shared.managers.ApplicationManager
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
        val studyGroupButton: Button = view.findViewById(R.id.button_study_groups)

        roomsButton.setOnClickListener{
            (context as MainActivity).addFragment(ApplicationManager.roomsFragment(null))
        }
        usersButton.setOnClickListener{
            (context as MainActivity).addFragment(ApplicationManager.usersFragment(null))
        }

        studyGroupButton.setOnClickListener {
            (context as MainActivity).addFragment(ApplicationManager.studyGroupsFragment(null))
        }

        return view
    }
}