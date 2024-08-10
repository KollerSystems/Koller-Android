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


class MessageBsdFragment : ScrollBsdFragment() {

    lateinit var contentBinding : FragmentBsdMessageBinding
    override fun getContent(inflater: LayoutInflater): ViewGroup {
        contentBinding = FragmentBsdMessageBinding.inflate(inflater)
        return contentBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("Igazgatói dícséret")
    }

    companion object {
        const val TAG = "MessageBottomSheet"
    }
}