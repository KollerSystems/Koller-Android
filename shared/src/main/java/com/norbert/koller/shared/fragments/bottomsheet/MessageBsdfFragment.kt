package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.databinding.ContentFragmentBsdfMessageBinding


class MessageBsdfFragment : ScrollBsdfFragment() {

    lateinit var contentBinding : ContentFragmentBsdfMessageBinding
    override fun getContent(inflater: LayoutInflater): ViewGroup {
        contentBinding = ContentFragmentBsdfMessageBinding.inflate(inflater)
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