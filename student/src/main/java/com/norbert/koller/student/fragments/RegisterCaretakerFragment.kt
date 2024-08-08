package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentDataCaretakerBinding
import com.norbert.koller.shared.databinding.ContentDataSchoolBinding

class RegisterCaretakerFragment : com.norbert.koller.student.fragments.RegisterFragment() {
    override fun checkIfAllCorrect(): Boolean {
        return true
    }

    lateinit var binding : ContentDataCaretakerBinding
    override fun createBinding(): ViewGroup {
        binding = ContentDataCaretakerBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}