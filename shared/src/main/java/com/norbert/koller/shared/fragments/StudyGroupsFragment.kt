package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.api.StudyGroupTypePagingSource
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.RoomRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.StudyGroupTypeRecyclerAdapter

class StudyGroupsFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getPagingSource(): BasePagingSource {
        return StudyGroupTypePagingSource(requireContext(), viewModel)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.abc, R.string.zyx,"Topic")

        baseRecycleAdapter = StudyGroupTypeRecyclerAdapter()
        baseRecycleAdapter.chipGroupSort = chipGroupSort
        baseRecycleAdapter.chipGroupFilter = chipGroupFilter


        addSearchbar("Topic")


        super.onViewCreated(view, savedInstanceState)
    }

}