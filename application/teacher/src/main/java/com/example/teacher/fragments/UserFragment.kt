package com.example.teacher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.shared.MyApplication
import com.example.teacher.R
import com.google.android.material.chip.Chip

class UserFragment : com.example.shared.fragments.UserFragment() {

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