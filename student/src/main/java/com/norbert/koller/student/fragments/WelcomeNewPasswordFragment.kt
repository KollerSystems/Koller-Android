package com.norbert.koller.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.fragments.WelcomeFragmentBase
import com.norbert.koller.student.R

class WelcomeNewPasswordFragment : WelcomeFragmentBase() {
    override fun checkIfAllCorrect(): Boolean {
        return true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_new_password, container, false)
    }
}