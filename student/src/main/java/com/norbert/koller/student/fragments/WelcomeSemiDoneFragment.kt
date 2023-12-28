package com.norbert.koller.student.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.shared.fragments.WelcomeFragmentBase
import com.norbert.koller.student.activities.MainActivity
import com.norbert.koller.student.R
import com.norbert.koller.student.activities.WelcomeActivity

class WelcomeSemiDoneFragment : WelcomeFragmentBase() {

    override fun checkIfAllCorrect(): Boolean {
        return true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_institution_id, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}