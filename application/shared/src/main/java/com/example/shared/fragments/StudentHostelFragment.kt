package com.example.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shared.R
import com.example.shared.navigateWithDefaultAnimation

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
            findNavController().navigateWithDefaultAnimation(R.id.action_studentHostelFragment_to_roomsFragment2)
        }

        usersButton.setOnClickListener{
            findNavController().navigateWithDefaultAnimation(R.id.action_studentHostelFragment_to_usersFragment2)
        }

        return view;
    }
}