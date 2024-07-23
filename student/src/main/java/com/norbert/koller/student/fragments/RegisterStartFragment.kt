package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentRegisterStartBinding

class RegisterStartFragment : Fragment() {

    lateinit var binding : FragmentRegisterStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterStartBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            (activity as com.norbert.koller.student.activities.RegisterActivity).scrollForward()
        }

        binding.btnBack.setOnClickListener {
            activity?.finish()
        }
    }
}