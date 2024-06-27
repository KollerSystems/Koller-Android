package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.checkbox.MaterialCheckBox
import com.norbert.koller.shared.R

class WelcomeSummaryFragment : WelcomeFragmentBase() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_summary, container, false)
    }

    lateinit var checkBoxRules : MaterialCheckBox
    lateinit var checkBoxTrueData : MaterialCheckBox
    lateinit var checkBoxParents : MaterialCheckBox

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBoxRules = view.findViewById(R.id.checkbox_rules)
        checkBoxTrueData = view.findViewById(R.id.checkbox_true_data)
        checkBoxParents = view.findViewById(R.id.checkbox_parents)

        waitForChange(checkBoxRules, checkBoxTrueData, checkBoxParents)
    }


    override fun checkIfAllCorrect() : Boolean{
        return (checkBoxRules.isChecked && checkBoxTrueData.isChecked && checkBoxParents.isChecked)
    }


}