package com.norbert.koller.shared.fragments

import com.norbert.koller.shared.data.UserData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.BaseViewModel
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.SuperCoolRecyclerView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.recycleradapter.UserRecyclerAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UsersFragment : Fragment() {

    private lateinit var superCoolRecyclerView: SuperCoolRecyclerView
    private var usersDataArrayList: ArrayList<TodayData> = ArrayList()

    private lateinit var leaderUsersRecyclerView: RecyclerView
    private lateinit var leaderUsersDataArrayList: ArrayList<UserData>


    lateinit var viewModel : BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_users, container, false)




        leaderUsersRecyclerView = view.findViewById(R.id.recycler_view_header)
        leaderUsersRecyclerView.setHasFixedSize(false)

        leaderUsersDataArrayList = arrayListOf(
            UserData(Name="Katona Márton"),
            UserData(Name="Härtlein Károly"),
            UserData(Name="Hatalmas Norbert")
        )

        leaderUsersRecyclerView.adapter = UserRecyclerAdapter(null)


        superCoolRecyclerView = view.findViewById(R.id.super_cool_recycler_view)

        val chipGroup : ChipGroup = view.findViewById(R.id.chip_group_sort)

        val userRecycleAdapter = UserRecyclerAdapter(chipGroup)



        viewModel = BaseViewModel { UserPagingSource(requireContext(), userRecycleAdapter, MyApplication.createApiSortString(chipGroup, "Name")) }

        superCoolRecyclerView.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userRecycleAdapter
        }


        superCoolRecyclerView.appBar = view.findViewById(R.id.appbar_layout)

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                userRecycleAdapter.submitData(pagingData)
            }
        }




        return view
    }
}

