package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.data.CanteenData
import com.norbert.koller.shared.databinding.FragmentBsdCanteenBinding
import com.norbert.koller.shared.viewmodels.ResponseViewModel

class CanteenBsdFragment(val canteenData: CanteenData? = null) : ScrollBsdFragment() {

    lateinit var binding : FragmentBsdCanteenBinding
    lateinit var viewModel: ResponseViewModel

    override fun getContent(inflater: LayoutInflater): ViewGroup {
        binding = FragmentBsdCanteenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        if(canteenData != null){
            viewModel.response.value = canteenData
        }

        setTitle((viewModel.response.value as CanteenData).foodName)
    }

    companion object {
        const val TAG = "CanteenBsdFragment"
    }
}