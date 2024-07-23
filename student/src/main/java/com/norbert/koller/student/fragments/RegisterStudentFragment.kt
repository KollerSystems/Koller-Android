package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentDataStudentBinding


class RegisterStudentFragment : RegisterFragment() {

    lateinit var binding : ContentDataStudentBinding

    override fun checkIfAllCorrect(): Boolean {
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ContentDataStudentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStudentId.setOnClickListener{
            val image = ImageView(requireContext())
            image.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            image.adjustViewBounds = true
            val padding = resources.getDimensionPixelSize(R.dimen.card_padding)
            image.setPadding(padding,padding,padding,padding)
            image.setImageResource(R.drawable.student_card)

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Üsd be az alábbi számsort a diákigazolványodról")
                .setView(image)
                .setPositiveButton(getString(R.string.understood)) { _, _ ->

                }
                .show()
        }
    }

}