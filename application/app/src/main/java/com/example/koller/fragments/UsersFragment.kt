package com.example.koller.fragments

import APIInterface
import UserData
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.MainActivity
import com.example.koller.R
import com.example.koller.TodayData
import com.example.koller.TodayRecyclerAdapter
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

        usersRecyclerView = view.findViewById(R.id.users_recycleview)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        usersRecyclerView.setHasFixedSize(true)

        val pageStart: Int = 0
        var isLoading: Boolean = false
        var isLastPage: Boolean = false
        var totalPages: Int = 100
        var currentPage: Int = pageStart

       fun loadNextPage(){
           isLoading = true
           val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
           usersResponse.getUsers(25, currentPage * 25, APIInterface.getHeaderMap()).enqueue(
               object : Callback<ArrayList<UserData>> {
                   override fun onResponse(
                       call: Call<ArrayList<UserData>>,
                       userResponse: Response<ArrayList<UserData>>
                   ) {
                       if(userResponse.code() == 200) {

                           var usersData : ArrayList<UserData> = userResponse.body()!!



                           for (i in 0 until usersData.size){
                               usersDataArrayList.add(TodayData(context?.getDrawable(R.drawable.person),usersData[i].Name, usersData[i].Group, usersDataArrayList.size.toString()))
                           }

                           usersRecyclerView.adapter = TodayRecyclerAdapter(usersDataArrayList)

                       }
                       else{
                           APIInterface.ServerErrorPopup(requireContext())
                       }

                       isLoading = false
                   }

                   override fun onFailure(call: Call<ArrayList<UserData>>, t: Throwable) {
                       APIInterface.ServerErrorPopup(requireContext())

                       isLoading = false
                   }
               }
           )
       }

        loadNextPage()

        usersRecyclerView.addOnScrollListener(object : PaginationScrollListener(usersRecyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {


                if(!isLoading) {

                    currentPage += 1
                    Toast.makeText(requireContext(), currentPage.toString(), Toast.LENGTH_SHORT)
                    loadNextPage()
                }

            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        return view
    }
}

abstract class PaginationScrollListener (layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    val layoutManager: LinearLayoutManager = layoutManager

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount: Int = layoutManager.childCount
        val totalItemCount: Int = layoutManager.itemCount
        val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun getTotalPageCount(): Int
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}
