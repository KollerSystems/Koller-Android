package com.norbert.koller.teacher.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.databinding.ContentFragmentBsdProfileFooterBinding
import com.norbert.koller.shared.databinding.ContentFragmentBsdProfileHeaderBinding
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.databinding.FragmentBsdProfileBinding

class ProfileBsdFragment : com.norbert.koller.shared.fragments.bottomsheet.ProfileBsdFragment() {

    lateinit var binding : FragmentBsdProfileBinding

    override fun getHeaderBinding(): ContentFragmentBsdProfileHeaderBinding {
        return binding.header
    }

    override fun getFooterBinding(): ContentFragmentBsdProfileFooterBinding {
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