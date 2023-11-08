package com.norbert.koller.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.checkbox.MaterialCheckBox
import com.norbert.koller.shared.fragments.WelcomeFragmentBase
import com.norbert.koller.student.activities.WelcomeActivity

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBoxRules = view.findViewById(R.id.checkbox_rules)
        checkBoxTrueData = view.findViewById(R.id.checkbox_true_data)

        waitForChange(checkBoxRules, checkBoxTrueData)
    }


    override fun checkIfAllCorrect() : Boolean{
        return (checkBoxRules.isChecked && checkBoxTrueData.isChecked)
    }


}