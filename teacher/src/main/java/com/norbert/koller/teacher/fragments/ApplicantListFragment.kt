package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectClass
import com.norbert.koller.shared.api.ApiDataObjectGroup
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.ClassData
import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.fragments.RoomTidinessListFragment
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter
import com.norbert.koller.teacher.activities.RoomRateActivity
import com.norbert.koller.teacher.recycleradapters.RoomTidinessRecyclerAdapter

class ApplicantListFragment : ListFragment() {

    override fun getFragmentTitle(): String {
        return getString(R.string.applicants)
    }

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectUser()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return UserRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.abc, R.string.zyx,"Name")

        addSortingChip("Type", R.string.state, arrayListOf(
            ListToggleItem(getString(R.string.waiting), null, AppCompatResources.getDrawable(requireContext(), R.drawable.hourglass), 0),
            ListToggleItem(getString(R.string.accepted), null, AppCompatResources.getDrawable(requireContext(), R.drawable.check_circle), 1),
            ListToggleItem(getString(R.string.rejected), null, AppCompatResources.getDrawable(requireContext(), R.drawable.x_circle), 2)
        ))

        addSortingChip("Gender", R.string.gender, arrayListOf(
            ListToggleItem(getString(R.string.woman), null, AppCompatResources.getDrawable(requireContext(), R.drawable.woman), 0),
            ListToggleItem(getString(R.string.man), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), 1)
        ))

        addSortingChip("Group.ID", R.string.group, ApiDataObjectGroup(), true)

        addSearchbar("Name")
    }
}