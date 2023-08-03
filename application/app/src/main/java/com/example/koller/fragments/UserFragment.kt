package com.example.koller.fragments

import com.example.shared.api.APIInterface
import com.example.shared.api.RetrofitHelper
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.shared.MyApplication
import com.example.koller.R
import com.example.shared.data.UserData

import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserFragment : com.example.shared.fragments.UserFragment() {


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