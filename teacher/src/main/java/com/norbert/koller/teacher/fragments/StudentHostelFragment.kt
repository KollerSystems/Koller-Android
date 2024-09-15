package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.R as Rs

class StudentHostelFragment : com.norbert.koller.shared.fragments.StudentHostelFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        addButton(getString(Rs.string.classes), com.norbert.koller.shared.R.drawable.groups, 2)
        addButton(getString(Rs.string.groups), com.norbert.koller.shared.R.drawable.parent, 3)

        addButton(getString(Rs.string.base_programs), com.norbert.koller.shared.R.drawable.base_program).setOnClickListener{
            getMainActivity().addFragment(BaseProgramTypesFragment())
        }

        addButton(getString(Rs.string.statistics), com.norbert.koller.shared.R.drawable.stats).setOnClickListener {
            getMainActivity().addFragment(StatisticsFragment())
        }
    }

}