package com.norbert.koller.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.RoomRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ResponseViewModel

open class OutgoingPermanentFragment(val userData: UserData? = null) : ListFragment() {

    override fun getPagingSource(): PagingSource {
        return UserPagingSource(requireContext(), getBaseViewModel())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.newest, R.string.oldest,"Time")

        apiRecyclerAdapter = UserRecyclerAdapter()
        apiRecyclerAdapter.chipsSort = binding.chipsSort
        apiRecyclerAdapter.chipsFilter = binding.chipsFilter




        addDateChip("Time")


        addSearchbar("Reason")


        super.onViewCreated(view, savedInstanceState)

        if(userData != null)
            getBaseViewModel().owner = userData
    }
}