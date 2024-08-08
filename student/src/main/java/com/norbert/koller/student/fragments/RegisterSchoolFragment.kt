package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.AttachView
import com.norbert.koller.shared.databinding.ContentDataSchoolBinding

class RegisterSchoolFragment : com.norbert.koller.student.fragments.RegisterFragment() {

    lateinit var binding : ContentDataSchoolBinding
    override fun createBinding(): ViewGroup {
        binding = ContentDataSchoolBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun checkIfAllCorrect(): Boolean {
        return true
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.attachSchool.setupCamera(this)
        binding.attachSchoolCertificate.setupCamera(this)
    }

}