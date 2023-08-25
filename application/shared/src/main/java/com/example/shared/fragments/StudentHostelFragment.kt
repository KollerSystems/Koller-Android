package com.example.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shared.R
import com.example.shared.activities.MainActivity
import com.example.shared.navigateWithDefaultAnimation

open class StudentHostelFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_student_hostel, container, false)

        val mainActivity = context as MainActivity
        mainActivity.setToolbarTitle(mainActivity.getString(R.string.student_hostel), null)
        mainActivity.changeSelectedBottomNavigationIcon(R.id.studentHostel)

        val roomsButton: Button = view.findViewById(R.id.student_hostel_button_rooms)
        val usersButton: Button = view.findViewById(R.id.student_hostel_button_users)

        roomsButton.setOnClickListener{
            (context as MainActivity).changeFragment(RoomsFragment())
        }

        usersButton.setOnClickListener{
            (context as MainActivity).changeFragment(UsersFragment())
        }

        return view;
    }
}