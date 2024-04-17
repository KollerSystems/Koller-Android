package com.norbert.koller.teacher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter
import com.norbert.koller.teacher.R

class ProgramParticipantsFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {

    override fun getPagingSource(): BasePagingSource {
        return UserPagingSource(requireContext(), viewModel)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(com.norbert.koller.shared.R.string.abc, com.norbert.koller.shared.R.string.zyx,"Name")
        baseRecycleAdapter = UserRecyclerAdapter()
        baseRecycleAdapter.chipGroupSort = chipGroupSort
        baseRecycleAdapter.chipGroupFilter = chipGroupFilter

        addSortingChip("Gender", com.norbert.koller.shared.R.string.gender, arrayListOf(
            ListItem(getString(com.norbert.koller.shared.R.string.woman), null, AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.woman), "0"),
            ListItem(getString(com.norbert.koller.shared.R.string.man), null, AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.man), "1")
        ))

        addSortingChip("Group.ID", com.norbert.koller.shared.R.string.group, { RetrofitInstance.api.getGroups()}, "group", true)

        addSortingChip("", com.norbert.koller.shared.R.string.quantity_of_completions, arrayListOf())

        addSearchbar("Name")

        super.onViewCreated(view, savedInstanceState)


    }

}