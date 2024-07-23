package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter

open class UsersFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : ListFragment(defaultFilters) {


    private lateinit var leaderUsersRecyclerView: RecyclerView
    private lateinit var leaderUsersDataArrayList: ArrayList<UserData>


    override fun getPagingSource(): PagingSource {
        return UserPagingSource(requireContext(), viewModel)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupSort(R.string.abc, R.string.zyx,"Name")
        apiRecyclerAdapter = UserRecyclerAdapter()
        apiRecyclerAdapter.chipGroupSort = binding.chipsSort
        apiRecyclerAdapter.chipGroupFilter = binding.chipsFilter

        /*leaderUsersRecyclerView = view.findViewById(R.id.recycler_view_header)
        leaderUsersRecyclerView.setHasFixedSize(false)

        leaderUsersDataArrayList = arrayListOf(
            UserData(name="Katona Márton"),
            UserData(name="Härtlein Károly"),
            UserData(name="Hatalmas Norbert")
        )

        leaderUsersRecyclerView.adapter = UserRecyclerAdapter(null)*/



        addSortingChip("Gender", R.string.gender, arrayListOf(
            ListItem(getString(R.string.woman), null, AppCompatResources.getDrawable(requireContext(), R.drawable.woman), "0"),
            ListItem(getString(R.string.man), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), "1")
        ))

        addSortingChip("Role", R.string.role, arrayListOf(
            ListItem(getString(R.string.student), null, null, "1"),
            ListItem(getString(R.string.teacher), null, null, "2")
        ))

        addSortingChip("Class.ID", R.string.class_, {RetrofitInstance.api.getClasses()}, "class")

        addSortingChip("Group.ID", R.string.group, {RetrofitInstance.api.getGroups()}, "group", true)

        addSearchbar("Name")

        super.onViewCreated(view, savedInstanceState)


    }
}
