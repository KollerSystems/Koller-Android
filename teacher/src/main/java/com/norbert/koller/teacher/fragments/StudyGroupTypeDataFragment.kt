package com.norbert.koller.teacher.fragments

import android.view.View
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.fragments.StudyGroupTypeFragment
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.databinding.FragmentProgramBinding
import com.norbert.koller.teacher.databinding.FragmentStudyGroupTypeBinding
import com.norbert.koller.teacher.databinding.FragmentStudyGroupTypeDataBinding

class StudyGroupTypeDataFragment() : StudyGroupTypeFragment() {

    lateinit var binding: FragmentStudyGroupTypeDataBinding

    override fun createRootView(): View {
        binding = FragmentStudyGroupTypeDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setupTransition(view: View, id : Int?) {

    }

    override fun getDataType(): Class<*> {
        return StudyGroupTypeData::class.java
    }

}