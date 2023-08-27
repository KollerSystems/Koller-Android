package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomFragmentPostTypes : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.bottom_fragment_post_types, container, false)
    }



    companion object {
        const val TAG = "PostTypesBottomSheet"
    }
}
