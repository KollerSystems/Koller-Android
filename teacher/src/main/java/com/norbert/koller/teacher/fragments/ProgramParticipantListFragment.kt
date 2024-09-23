package com.norbert.koller.teacher.fragments

import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter

class ProgramParticipantListFragment() : ListFragment() {

    override fun getPagingSource(): PagingSource {
        return UserPagingSource(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return UserRecyclerAdapter()
    }


    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(com.norbert.koller.shared.R.string.abc, com.norbert.koller.shared.R.string.zyx,"Name")

        addSortingChip("Gender", com.norbert.koller.shared.R.string.gender, arrayListOf(
            ListItem(getString(com.norbert.koller.shared.R.string.woman), null, AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.woman), "0"),
            ListItem(getString(com.norbert.koller.shared.R.string.man), null, AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.man), "1")
        ))

        addSortingChip("Group.ID", com.norbert.koller.shared.R.string.group, { RetrofitInstance.api.getGroups()}, GroupData::class.java, true)

        addSortingChip("", com.norbert.koller.shared.R.string.quantity_of_completions, arrayListOf())

        addSearchbar("Name")

    }

    override fun getFragmentTitle(): String? {
         return null
    }

}