package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.shared.data.UserData
import com.example.shared.recycleradapter.UserPreviewRecyclerAdapter


class RoomFragment : com.example.shared.fragments.RoomFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_room, container, false)


        return view
    }

}