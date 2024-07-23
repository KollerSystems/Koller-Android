package com.norbert.koller.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentRegisterFinishBinding

class RegisterFinishFragment : Fragment() {

    lateinit var binding : FragmentRegisterFinishBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterFinishBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExit.setOnClickListener{
            (requireContext() as com.norbert.koller.student.activities.RegisterActivity).finishAffinity()
        }
    }
}