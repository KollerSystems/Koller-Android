package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.customview.ExtraEditText
import com.norbert.koller.shared.customview.SuperCoolRecyclerView
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.FilterBaseData
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.recycleradapter.BasePagingSource
import com.norbert.koller.shared.viewmodels.BaseViewModel
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.recycleradapter.RoomRecyclerAdapter
import com.norbert.koller.shared.setVisibilityBy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


open class RoomsFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : FragmentList(defaultFilters) {

    override fun getPagingSource(): BasePagingSource {
        return RoomPagingSource(requireContext(), viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.from_down, R.string.from_up,"RID")

        baseRecycleAdapter = RoomRecyclerAdapter(chipGroupSort, chipGroupFilter)

        addSortingChip("Gender", R.string.side, arrayListOf(
            ListItem( getString(R.string.girl), null, AppCompatResources.getDrawable(requireContext(), R.drawable.woman), "0"),
            ListItem( getString(R.string.boy), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), "1")
        ))

        addSearchbar()


        super.onViewCreated(view, savedInstanceState)
    }
}