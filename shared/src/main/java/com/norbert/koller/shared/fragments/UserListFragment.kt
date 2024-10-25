package com.norbert.koller.shared.fragments

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.ClassData
import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.ListToggleItem
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.recycleradapters.UserRecyclerAdapter

open class UserListFragment() : ListFragment() {


    override fun getFragmentTitle(): String? {
        return getString(R.string.users)
    }



    private lateinit var leaderUsersRecyclerView: RecyclerView
    private lateinit var leaderUsersDataArrayList: ArrayList<UserData>


    override fun getPagingSource(): PagingSource {
        return UserPagingSource(requireContext(), getBaseViewModel())
    }

    override fun getPagingSourceWithSeparator(): PagingSourceWithSeparator {
        return PagingSourceWithSeparator(ApiDataObjectUser(), requireContext(), getBaseViewModel())
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
            ListToggleItem(getString(R.string.woman), null, AppCompatResources.getDrawable(requireContext(), R.drawable.woman), "0"),
            ListToggleItem(getString(R.string.man), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), "1")
        ))

        addSortingChip("Role", R.string.role, arrayListOf(
            ListToggleItem(getString(R.string.student), null, null, "1"),
            ListToggleItem(getString(R.string.teacher), null, null, "2")
        ))

        addSortingChip("Class.ID", R.string.class_, {RetrofitInstance.api.getClasses()}, ClassData::class.java)

        addSortingChip("Group.ID", R.string.group, {RetrofitInstance.api.getGroups()}, GroupData::class.java, true)

        addSearchbar("Name")
    }
}
