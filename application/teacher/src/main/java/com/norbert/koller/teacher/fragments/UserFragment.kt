package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.teacher.R
import com.google.android.material.chip.Chip

class UserFragment : com.norbert.koller.shared.fragments.UserFragment() {

    override fun showAndSetIfNotNull(card : View, string : String?){
        if (!string.isNullOrBlank()) {
            card.visibility = View.VISIBLE
            (card as Chip).text = string
            card.setOnClickListener{
                MyApplication.setClipboard(requireContext(), string)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }
}