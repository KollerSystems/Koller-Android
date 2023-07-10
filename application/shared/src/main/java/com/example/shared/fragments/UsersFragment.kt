package com.example.shared.fragments

import com.example.shared.api.APIInterface
import com.example.shared.data.UserData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.R
import com.example.shared.TipView
import com.example.shared.api.RetrofitHelper
import com.example.shared.data.TodayData
import com.example.shared.recycleradapter.UserRecycleAdapter
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


        val tipView : TipView = view.findViewById(R.id.tip)

        leaderUsersRecyclerView = view.findViewById(R.id.recycler_view_header)
        leaderUsersRecyclerView.setHasFixedSize(false)

        leaderUsersDataArrayList = arrayListOf(
            UserData("Katona Márton"),
            UserData("Härtlein Károly"),
            UserData("Hatalmas Norbert")
        )

        leaderUsersRecyclerView.adapter = UserRecycleAdapter(
            leaderUsersDataArrayList,
            requireContext(),
            true
        )


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

                            usersRecyclerView.adapter =
                                UserRecycleAdapter(
                                    usersData,
                                    requireContext()
                                )

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

