package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.teacher.R

class StudentHostelFragment : com.norbert.koller.shared.fragments.StudentHostelFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val baseProgramsButton : Button = view.findViewById(com.norbert.koller.shared.R.id.button_base_programs)
        baseProgramsButton.isVisible = true

        baseProgramsButton.setOnClickListener{
            (requireContext() as MainActivity).addFragment(BaseProgramsFragment())
        }
    }

}