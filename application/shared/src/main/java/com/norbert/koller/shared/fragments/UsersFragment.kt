package com.norbert.koller.shared.fragments

import android.content.Context
import android.icu.text.SimpleDateFormat
import com.norbert.koller.shared.data.UserData
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.recycleradapter.BaseViewModel
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitHelper
import com.norbert.koller.shared.customview.SuperCoolRecyclerView
import com.norbert.koller.shared.api.UserPagingSource
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.FilterDateData
import com.norbert.koller.shared.data.FiltersData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.recycleradapter.BaseRecycleAdapter
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.recycleradapter.UserRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

open class UsersFragment : Fragment() {

    private lateinit var superCoolRecyclerView: SuperCoolRecyclerView

    private lateinit var leaderUsersRecyclerView: RecyclerView
    private lateinit var leaderUsersDataArrayList: ArrayList<UserData>

    var userRecycleAdapter : UserRecyclerAdapter? = null
    var viewModel : BaseViewModel? = null


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

        val chipGroupSort : ChipGroup = view.findViewById(R.id.chip_group_sort)

        val chipGender : Chip = view.findViewById(R.id.chip_gender)

        val chipRole : Chip = view.findViewById(R.id.chip_role)


            userRecycleAdapter = UserRecyclerAdapter(chipGroupSort, listOf(chipGender, chipRole))
            viewModel = BaseViewModel({ UserPagingSource(requireContext(), userRecycleAdapter!!) })



        chipGender.connectToCheckBoxList(childFragmentManager, "Gender", R.string.gender, arrayListOf(
            ListItem(getString(R.string.woman), null, AppCompatResources.getDrawable(requireContext(), R.drawable.woman), "0"),
            ListItem(getString(R.string.man), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), "1")
        ))

        chipRole.connectToCheckBoxList(childFragmentManager, "Role", R.string.role, arrayListOf(
            ListItem(getString(R.string.student), null, null, "1"),
            ListItem(getString(R.string.teacher), null, null, "2")
        ))




        superCoolRecyclerView.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userRecycleAdapter
        }


        superCoolRecyclerView.appBar = view.findViewById(R.id.appbar_layout)


        lifecycleScope.launch {

            viewModel!!.pagingData.collectLatest { pagingData ->
                userRecycleAdapter!!.submitData(pagingData)
            }
        }




        return view
    }

    override fun onStop() {
        super.onStop()

    }
}
