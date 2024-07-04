package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.AttachView

class InformationSchoolFragment : WelcomeFragmentBase() {

    override fun checkIfAllCorrect(): Boolean {
        return true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.norbert.koller.shared.R.layout.fragment_information_school, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val attachSchool : AttachView = view.findViewById(R.id.attach_school)
        val attachSchoolCertificate : AttachView = view.findViewById(R.id.attach_school_certificate)

        attachSchool.setupCamera(this)
        attachSchoolCertificate.setupCamera(this)
    }

}