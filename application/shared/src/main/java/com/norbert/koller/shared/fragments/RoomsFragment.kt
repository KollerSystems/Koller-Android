package com.norbert.koller.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.BaseViewModel
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.SuperCoolRecyclerView
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.recycleradapter.RoomRecyclerAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RoomsFragment : Fragment() {

    private lateinit var roomsRecyclerView: SuperCoolRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomsRecyclerView = view.findViewById(R.id.super_cool_recycler_view)
        roomsRecyclerView.recyclerView.layoutManager = LinearLayoutManager(context)

        val chipGroup : ChipGroup = view.findViewById(R.id.chip_group_sort)




        val roomRecycleAdapter = RoomRecyclerAdapter(chipGroup)
        val viewModel = BaseViewModel { RoomPagingSource(requireContext(), roomRecycleAdapter,MyApplication.createApiSortString(chipGroup, "RID")) }


        roomsRecyclerView.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomRecycleAdapter
        }


        roomsRecyclerView.appBar = view.findViewById(R.id.appbar_layout)

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                roomRecycleAdapter.submitData(pagingData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_rooms, container, false)
    }
}