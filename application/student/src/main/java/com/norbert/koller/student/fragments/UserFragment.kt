package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.student.R


class UserFragment : com.norbert.koller.shared.fragments.UserFragment() {


    override fun showAndSetIfNotNull(card : View, string : String?){
        if (!string.isNullOrBlank()) {
            card.visibility = VISIBLE
            (((card as ViewGroup).getChildAt(0) as ViewGroup).getChildAt(2) as TextView).text = string
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