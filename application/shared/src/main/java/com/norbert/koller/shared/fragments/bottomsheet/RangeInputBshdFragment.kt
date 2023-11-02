package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.R

class RangeInputBshdFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_bshd_range_input, container, false)
    }



    companion object {
        const val TAG = "RangeInputBottomSheet"
    }
}