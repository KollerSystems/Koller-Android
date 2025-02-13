package com.norbert.koller.shared.fragments.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import com.norbert.koller.shared.databinding.ContentFragmentBsdfOutgoingBinding

class OutgoingBsdf : BsdfFragment() {
    lateinit var contentBinding : ContentFragmentBsdfOutgoingBinding

    override fun getContentHolder(inflater: LayoutInflater): ViewGroup {
        contentBinding = ContentFragmentBsdfOutgoingBinding.inflate(layoutInflater)
        return contentBinding.root
    }
}