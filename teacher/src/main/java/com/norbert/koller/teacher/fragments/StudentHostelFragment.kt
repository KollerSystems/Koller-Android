package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.norbert.koller.shared.activities.MainActivity

class StudentHostelFragment : com.norbert.koller.shared.fragments.StudentHostelFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.btnBasePrograms.isVisible = true

        binding.btnBasePrograms.setOnClickListener{
            (requireContext() as MainActivity).addFragment(BaseProgramTypesFragment())
        }
    }

}