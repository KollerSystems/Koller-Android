package com.norbert.koller.shared.fragments

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectClass
import com.norbert.koller.shared.api.ApiDataObjectGroup
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.ClassData
import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter

open class UserListFragment() : ListFragment() {


    override fun getFragmentTitle(): String? {
        return getString(R.string.users)
    }



    private lateinit var leaderUsersRecyclerView: RecyclerView
    private lateinit var leaderUsersDataArrayList: ArrayList<UserData>


    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectUser()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }


    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return UserRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        /*leaderUsersRecyclerView = view.findViewById(R.id.recycler_view_header)
        leaderUsersRecyclerView.setHasFixedSize(false)

        leaderUsersDataArrayList = arrayListOf(
            UserData(name="Katona Márton"),
            UserData(name="Härtlein Károly"),
            UserData(name="Hatalmas Norbert")
        )

        leaderUsersRecyclerView.adapter = UserRecyclerAdapter(null)*/

        setupSort(R.string.abc, R.string.zyx,"Name")

        addSortingChip("Gender", R.string.gender, arrayListOf(
            ListToggleItem(getString(R.string.woman), null, AppCompatResources.getDrawable(requireContext(), R.drawable.woman), 0),
            ListToggleItem(getString(R.string.man), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), 1)
        ))

        addSortingChip("Role", R.string.role, arrayListOf(
            ListToggleItem(getString(R.string.student), null, null, 1),
            ListToggleItem(getString(R.string.teacher), null, null, 2)
        ))

        addSortingChip("Class.ID", R.string.class_, ApiDataObjectClass())

        addSortingChip("Group.ID", R.string.group, ApiDataObjectGroup(), true)

        addSearchbar("Name")
    }
}
