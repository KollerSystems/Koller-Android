package com.norbert.koller.student.fragments

import com.norbert.koller.shared.fragments.StudyGroupTypeFragment
import com.norbert.koller.student.R

class StudyGroupTypeFragment(pid : Int? = null) : StudyGroupTypeFragment(pid) {

    override fun getLayout(): Int {
        return R.layout.fragment_study_group_type
    }

}