package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.R
import com.example.shared.data.UserData
import com.example.shared.recycleradapter.UserPreviewRecyclerAdapter


class RoomFragment : Fragment() {


    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var userDataArrayList: ArrayList<UserData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(com.example.koller.R.layout.fragment_room, container, false)

        usersRecyclerView = view.findViewById(R.id.recycler_view)
        usersRecyclerView.setHasFixedSize(false)

        userDataArrayList = arrayListOf(
            UserData("Katona Márton"),
            UserData("Härtlein Károly"),
            UserData("Hatalmas Norbert")
        )

        usersRecyclerView.adapter = UserPreviewRecyclerAdapter(
            userDataArrayList,
            requireContext()
        )



        return view
    }

}