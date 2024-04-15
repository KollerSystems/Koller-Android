package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.BaseProgramTypePagingSource
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.api.StudyGroupTypePagingSource
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.RoomRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.StudyGroupTypeRecyclerAdapter
import com.norbert.koller.teacher.recycleradapters.BaseProgramTypeRecyclerAdapter

class BaseProgramsFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getPagingSource(): BasePagingSource {
        return BaseProgramTypePagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.abc, R.string.zyx,"Topic")
        baseRecycleAdapter = BaseProgramTypeRecyclerAdapter()
        baseRecycleAdapter.chipGroupSort = chipGroupSort
        baseRecycleAdapter.chipGroupFilter = chipGroupFilter


        addSearchbar("Topic")

        super.onViewCreated(view, savedInstanceState)
    }

}