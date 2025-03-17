package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentFragmentBsdfOutgoingBinding

class OutgoingBsdf : BsdfFragment() {
    lateinit var contentBinding : ContentFragmentBsdfOutgoingBinding

    override fun getContentHolder(inflater: LayoutInflater): ViewGroup {
        contentBinding = ContentFragmentBsdfOutgoingBinding.inflate(layoutInflater)
        return contentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.outgoing))
    }

    companion object {
        const val TAG = "OutgoingBsdf"
    }
}