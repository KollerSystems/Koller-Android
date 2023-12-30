package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.WelcomeFragmentBase

class WelcomeBasicInformationsFragment : WelcomeFragmentBase() {
    override fun checkIfAllCorrect(): Boolean {
        return true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_informations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}