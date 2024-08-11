package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.checkbox.MaterialCheckBox
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentRegisterSummaryBinding

class RegisterSummaryFragment : RegisterFragment() {

    lateinit var binding : FragmentRegisterSummaryBinding

    override fun createBinding(): ViewGroup {
        binding = FragmentRegisterSummaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        waitForChange(binding.cbRules, binding.cbTrueData, binding.cbParents)

        binding.btnEditStudentHostel.setOnClickListener {
            getRegisterActivity().binding.viewPager.setCurrentItem(1, true)
        }

        binding.personalData.btnEditStudent.setOnClickListener {
            getRegisterActivity().binding.viewPager.setCurrentItem(2, true)
        }

        binding.personalData.btnEditCaretaker.setOnClickListener {
            getRegisterActivity().binding.viewPager.setCurrentItem(3, true)
        }

        binding.personalData.btnEditCloseRelative.setOnClickListener {
            getRegisterActivity().binding.viewPager.setCurrentItem(4, true)
        }

        binding.personalData.btnEditSchool.setOnClickListener {
            getRegisterActivity().binding.viewPager.setCurrentItem(5, true)
        }
    }


    override fun checkIfAllCorrect() : Boolean{
        return (binding.cbRules.isChecked && binding.cbTrueData.isChecked && binding.cbParents.isChecked)
    }


}