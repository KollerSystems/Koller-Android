package com.example.koller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koller.MyApplication
import com.example.koller.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MessageFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view : View = inflater.inflate(R.layout.fragment_message, container, false)

        MyApplication.setupBottomSheet(dialog!!)

        return view
    }

    companion object {
        const val TAG = "MessageBottomSheet"
    }
}