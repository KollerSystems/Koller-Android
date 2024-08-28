package com.norbert.koller.teacher.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.databinding.ContentFragmentBsdfProfileFooterBinding
import com.norbert.koller.shared.databinding.ContentFragmentBsdfProfileHeaderBinding
import com.norbert.koller.teacher.databinding.FragmentBsdProfileBinding

class ProfileBsdfFragment : com.norbert.koller.shared.fragments.bottomsheet.ProfileBsdfFragment() {

    lateinit var binding : FragmentBsdProfileBinding

    override fun getHeaderBinding(): ContentFragmentBsdfProfileHeaderBinding {
        return binding.header
    }

    override fun getFooterBinding(): ContentFragmentBsdfProfileFooterBinding {
        return binding.footer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getContent(inflater: LayoutInflater): ViewGroup {
        binding = FragmentBsdProfileBinding.inflate(layoutInflater)
        return binding.root
    }

}