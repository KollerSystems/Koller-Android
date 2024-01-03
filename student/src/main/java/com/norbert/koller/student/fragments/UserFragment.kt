package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.student.R


class UserFragment(uid : Int? = null) : com.norbert.koller.shared.fragments.UserFragment(uid) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getLayout(): Int {
        return R.layout.fragment_user
    }


}