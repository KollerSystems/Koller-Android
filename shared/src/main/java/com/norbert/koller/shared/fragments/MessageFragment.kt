package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.setupBottomSheet


class MessageFragment : BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_bshd_message, container, false)

        dialog!!.setupBottomSheet()

        return view
    }

    companion object {
        const val TAG = "MessageBottomSheet"
    }
}