package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.CanteenData
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.CanteenRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.ListToggleItem
import com.norbert.koller.shared.recycleradapters.PagingSource
import java.util.Date

class CanteenFragment : ListFragment() {

    override fun getFragmentTitle(): String? {
        return null
    }

    override fun getPagingSource(): PagingSource {
        return UserPagingSource(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return CanteenRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()
        setupSort(R.string.newest, R.string.oldest,"Name")
        addSortingChip("type", R.string.type_, arrayListOf(
            ListToggleItem(getString(R.string.breakfast), null, AppCompatResources.getDrawable(requireContext(), R.drawable.breakfast), "1"),
            ListToggleItem(getString(R.string.lunch), null, AppCompatResources.getDrawable(requireContext(), R.drawable.lunch), "0"),
            ListToggleItem(getString(R.string.dinner), null, AppCompatResources.getDrawable(requireContext(), R.drawable.dinner), "2")
        ))
        addDateChip()
    }

}