package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.student.R


class RoomFragment(rid : Int? = null) : com.norbert.koller.shared.fragments.RoomFragment(rid) {
    override fun getLayout(): Int {
        return R.layout.fragment_room
    }

}