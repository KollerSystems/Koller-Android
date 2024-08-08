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
import com.norbert.koller.shared.databinding.FragmentStudentHostelBinding

open class StudentHostelFragment : FragmentInMainActivity() {

    lateinit var binding : FragmentStudentHostelBinding

    override fun getFragmentTitle(): String? {
        return getString(R.string.student_hostel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentHostelBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPlaces.setOnClickListener{
            (context as MainActivity).addFragment(ApplicationManager.roomsFragment(null))
        }

        binding.btnUsers.setOnClickListener{
            (context as MainActivity).addFragment(ApplicationManager.usersFragment(null))
        }

        binding.btnStudyGroups.setOnClickListener {
            (context as MainActivity).addFragment(ApplicationManager.studyGroupsFragment(null))
        }
    }
}