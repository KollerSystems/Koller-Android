package com.norbert.koller.student.fragments

import android.view.View
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.fragments.StudyGroupTypeFragment
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentStudyGroupTypeBinding

class StudyGroupTypeFragment() : StudyGroupTypeFragment() {

    lateinit var binding : FragmentStudyGroupTypeBinding

    override fun getDataType(): Class<*> {
        return StudyGroupTypeData::class.java
    }

    override fun createRootView(): View {
        binding = FragmentStudyGroupTypeBinding.inflate(layoutInflater)
        return binding.root
    }

}