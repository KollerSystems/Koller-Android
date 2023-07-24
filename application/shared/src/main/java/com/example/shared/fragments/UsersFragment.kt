package com.example.shared.fragments

import com.example.shared.api.APIInterface
import com.example.shared.data.UserData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.R
import com.example.shared.SuperCoolRecyclerView
import com.example.shared.TipView
import com.example.shared.UserViewModel
import com.example.shared.api.RetrofitHelper
import com.example.shared.data.TodayData
import com.example.shared.recycleradapter.UserRecycleAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersFragment : Fragment() {

    private lateinit var superCoolRecyclerView: SuperCoolRecyclerView
    private var usersDataArrayList: ArrayList<TodayData> = ArrayList()

    private lateinit var leaderUsersRecyclerView: RecyclerView
    private lateinit var leaderUsersDataArrayList: ArrayList<UserData>


    lateinit var viewModel : UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_users, container, false)

        leaderUsersRecyclerView = view.findViewById(R.id.recycler_view_header)
        leaderUsersRecyclerView.setHasFixedSize(false)

        leaderUsersDataArrayList = arrayListOf(
            UserData("Katona Márton"),
            UserData("Härtlein Károly"),
            UserData("Hatalmas Norbert")
        )

        leaderUsersRecyclerView.adapter = UserRecycleAdapter()


        superCoolRecyclerView = view.findViewById(R.id.super_cool_recycler_view)

        val userRecycleAdapter = UserRecycleAdapter()

        viewModel = UserViewModel(userRecycleAdapter)

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

