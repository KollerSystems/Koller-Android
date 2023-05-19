package com.example.koller.fragments

import APIInterface
import UserData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.koller.R
import com.example.koller.data.TodayData
import com.example.koller.recycleradapter.UserRecycleAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersFragment : Fragment() {

    private lateinit var usersRecyclerView: RecyclerView
    private var usersDataArrayList: ArrayList<TodayData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_users, container, false)

        val refresh : SwipeRefreshLayout = view.findViewById(R.id.users_refresh)

        var isLoading: Boolean = false
        var isLastPage: Boolean = false
        var currentPage: Int = 0

        fun loadNextPage() {
            isLoading = true
            val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
            usersResponse.getUsers(25, currentPage * 25, APIInterface.getHeaderMap()).enqueue(
                object : Callback<List<UserData>> {
                    override fun onResponse(
                        call: Call<List<UserData>>,
                        userResponse: Response<List<UserData>>
                    ) {
                        if (userResponse.code() == 200) {

                            var usersData: List<UserData> = userResponse.body()!!

                            for (i in usersData.indices) {
                                usersDataArrayList.add(
                                    TodayData(
                                        context?.getDrawable(R.drawable.person),
                                        usersData[i].Name,
                                        usersData[i].ID.toString(),
                                        usersDataArrayList.size.toString()
                                    )
                                )
                            }

                            usersRecyclerView.adapter = UserRecycleAdapter(usersDataArrayList, requireContext())

                        } else {
                            APIInterface.ServerErrorPopup(requireContext())
                        }

                        isLoading = false
                        refresh.isRefreshing = false
                    }

                    override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                        APIInterface.ServerErrorPopup(requireContext())

                        isLoading = false
                        refresh.isRefreshing = false
                    }
                }
            )
        }


        usersRecyclerView = view.findViewById(R.id.users_recycleview)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        usersRecyclerView.setHasFixedSize(true)

        refresh.setOnRefreshListener{
            usersDataArrayList = ArrayList()
            loadNextPage()
        }


        loadNextPage()

        return view
    }
}

