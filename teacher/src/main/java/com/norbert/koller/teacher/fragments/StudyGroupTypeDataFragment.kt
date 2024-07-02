package com.norbert.koller.teacher.fragments

import android.view.View
import com.norbert.koller.shared.fragments.StudyGroupTypeFragment
import com.norbert.koller.teacher.R

class StudyGroupTypeDataFragment(pid : Int? = null) : StudyGroupTypeFragment(pid) {

    override fun getLayout(): Int {
        return R.layout.fragment_study_group_type_data
    }

    override fun setupTransition(view: View) {

    }

}