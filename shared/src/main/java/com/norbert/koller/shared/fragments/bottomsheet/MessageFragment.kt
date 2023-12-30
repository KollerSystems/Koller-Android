package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.managers.setupBottomSheet


class MessageFragment : BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bshd_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setupBottomSheet()
    }

    companion object {
        const val TAG = "MessageBottomSheet"
    }
}