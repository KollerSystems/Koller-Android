package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.norbert.koller.shared.databinding.ContentFragmentUserHeaderBinding
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentUserBinding


class UserFragment(uid : Int? = null) : com.norbert.koller.shared.fragments.UserFragment(uid) {

    lateinit var binding : FragmentUserBinding

    override fun getHeaderBinding(): ContentFragmentUserHeaderBinding {
        return binding.header
    }

    override fun getNestedScrollView(): NestedScrollView {
        return binding.nsv
    }

    override fun createRootView(): View {
        binding = FragmentUserBinding.inflate(layoutInflater)
        return binding.root
    }


}