package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.databinding.FragmentBsdMessageBinding
import com.norbert.koller.shared.databinding.FragmentBsdTitleBinding
import com.norbert.koller.shared.managers.setupBottomSheet


class MessageBsdFragment : BottomSheetDialogFragment() {

    lateinit var containerBinding : FragmentBsdTitleBinding
    lateinit var contentBinding : FragmentBsdMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        containerBinding = FragmentBsdTitleBinding.inflate(inflater)
        contentBinding = FragmentBsdMessageBinding.inflate(inflater)
        containerBinding.scrollView.addView(contentBinding.root)
        return containerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setupBottomSheet()
    }

    companion object {
        const val TAG = "MessageBottomSheet"
    }
}