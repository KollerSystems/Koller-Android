package com.example.koller.fragments

import com.example.koller.api.APIInterface
import com.example.koller.data.UserData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.koller.R
import com.example.koller.api.RetrofitHelper
import com.example.koller.data.TodayData
import com.example.koller.recycleradapter.UserPreviewRecyclerAdapter
import com.example.koller.recycleradapter.UserRecycleAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersFragment : Fragment() {

    private lateinit var usersRecyclerView: RecyclerView
    private var usersDataArrayList: ArrayList<TodayData> = ArrayList()

    private lateinit var leaderUsersRecyclerView: RecyclerView
    private lateinit var leaderUsersDataArrayList: ArrayList<UserData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_users, container, false)


        leaderUsersRecyclerView = view.findViewById(R.id.recycler_view_header)
        leaderUsersRecyclerView.setHasFixedSize(false)

        leaderUsersDataArrayList = arrayListOf(
            UserData("Katona Márton"),
            UserData("Härtlein Károly"),
            UserData("Hatalmas Norbert")
        )

        leaderUsersRecyclerView.adapter = UserRecycleAdapter(leaderUsersDataArrayList, requireContext(), true)


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

                            val usersData: List<UserData> = userResponse.body()!!

                            usersRecyclerView.adapter = UserRecycleAdapter(usersData, requireContext())

                        } else {
                            APIInterface.ServerErrorPopup(requireContext())
                        }

                        isLoading = false
                    }

                    override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
                        APIInterface.ServerErrorPopup(context)

                        isLoading = false
                    }
                }
            )
        }


        usersRecyclerView = view.findViewById(R.id.recycler_view)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        usersRecyclerView.setHasFixedSize(true)



        loadNextPage()

        return view
    }
}

