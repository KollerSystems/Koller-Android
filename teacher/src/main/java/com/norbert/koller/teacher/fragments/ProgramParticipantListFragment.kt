package com.norbert.koller.teacher.fragments

import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectClass
import com.norbert.koller.shared.api.ApiDataObjectGroup
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter

class ProgramParticipantListFragment() : ListFragment() {

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectUser()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return UserRecyclerAdapter()
    }


    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(com.norbert.koller.shared.R.string.abc, com.norbert.koller.shared.R.string.zyx,"Name")

        addSortingChip("Gender", com.norbert.koller.shared.R.string.gender, arrayListOf(
            ListToggleItem(getString(com.norbert.koller.shared.R.string.woman), null, AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.woman), 0),
            ListToggleItem(getString(R.string.man), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), 1)
        ))

        addSortingChip("Group.ID", com.norbert.koller.shared.R.string.group, ApiDataObjectGroup(), true)

        addSortingChip("", com.norbert.koller.shared.R.string.quantity_of_completions, arrayListOf())

        addSearchbar("Name")

    }

    override fun getFragmentTitle(): String? {
         return null
    }

}