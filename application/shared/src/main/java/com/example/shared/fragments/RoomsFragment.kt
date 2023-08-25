package com.example.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.BaseViewModel
import com.example.shared.R
import com.example.shared.SuperCoolRecyclerView
import com.example.shared.activities.MainActivity
import com.example.shared.api.RoomPagingSource
import com.example.shared.api.UserPagingSource
import com.example.shared.data.TodayData
import com.example.shared.recycleradapter.RoomRecyclerAdapter
import com.example.shared.recycleradapter.UserRecyclerAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RoomsFragment : Fragment() {

    private lateinit var roomsRecyclerView: SuperCoolRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_rooms, container, false)

        val mainActivity = context as MainActivity
        mainActivity.setToolbarTitle(mainActivity.getString(R.string.rooms), mainActivity.getString(R.string.student_hostel))
        mainActivity.changeSelectedBottomNavigationIcon(R.id.studentHostel)

        roomsRecyclerView = view.findViewById(R.id.super_cool_recycler_view)
        roomsRecyclerView.recyclerView.layoutManager = LinearLayoutManager(context)


        val roomRecycleAdapter = RoomRecyclerAdapter()
        val viewModel = BaseViewModel { RoomPagingSource(requireContext(), roomRecycleAdapter) }


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

        return view
    }
}