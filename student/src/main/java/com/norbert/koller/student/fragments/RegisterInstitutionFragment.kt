package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentRegisterInstitutionBinding

class RegisterInstitutionFragment : RegisterFragment() {

    override fun checkIfAllCorrect(): Boolean {
        return true
    }


    lateinit var binding : FragmentRegisterInstitutionBinding
    override fun createBinding(): ViewGroup {
        binding = FragmentRegisterInstitutionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}