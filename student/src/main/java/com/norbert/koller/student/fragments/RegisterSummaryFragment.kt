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
    }


    override fun checkIfAllCorrect() : Boolean{
        return (binding.cbRules.isChecked && binding.cbTrueData.isChecked && binding.cbParents.isChecked)
    }


}