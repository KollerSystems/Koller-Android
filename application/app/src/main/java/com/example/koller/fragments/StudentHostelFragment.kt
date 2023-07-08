package com.example.koller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shared.navigateWithDefaultAnimation

class StudentHostelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(com.example.shared.R.layout.fragment_student_hostel, container, false)

        val roomsButton: Button = view.findViewById(com.example.shared.R.id.student_hostel_button_rooms)
        val usersButton: Button = view.findViewById(com.example.shared.R.id.student_hostel_button_users)

        roomsButton.setOnClickListener{
            findNavController().navigateWithDefaultAnimation(com.example.koller.R.id.action_studentHostelFragment_to_roomsFragment2)
        }

        usersButton.setOnClickListener{
            findNavController().navigateWithDefaultAnimation(com.example.koller.R.id.action_studentHostelFragment_to_usersFragment2)
        }

        return view;
    }
}