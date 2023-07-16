package com.example.teacher.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teacher.R

class ProfileBottomSheet : com.example.shared.fragments.bottomsheet.ProfileBottomSheet() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        realView = inflater.inflate(R.layout.bottom_sheet_profile, container, false)

        super.onCreateView(inflater, container, savedInstanceState)

        return  realView
    }

}