package com.norbert.koller.student.fragments

import android.view.View
import com.norbert.koller.shared.fragments.StudyGroupTypeFragment
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentStudyGroupTypeBinding

class StudyGroupTypeFragment(pid : Int? = null) : StudyGroupTypeFragment(pid) {

    lateinit var binding : FragmentStudyGroupTypeBinding

    override fun createRootView(): View {
        binding = FragmentStudyGroupTypeBinding.inflate(layoutInflater)
        return binding.root
    }

}