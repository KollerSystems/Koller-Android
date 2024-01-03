package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.FullScreenLoading
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.UserPreviewRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.skydoves.androidveil.VeilLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class RoomFragment(val rid: Int? = null) : DetailsFragment(rid) {

    lateinit var usersRecyclerView: RecyclerView
    lateinit var userDataArrayList: ArrayList<UserData>
    lateinit var textTitle : TextView
    lateinit var buttonDesc : Button

    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }

    override fun getDataTag(): String {
        return "room"
    }

    override fun getVeils(): List<VeilLayout> {
        return listOf()
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return {RetrofitInstance.api.getRoom(viewModel.id!!)}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("MEG VAGYXOK CSIHNÁLVA", "FASDFIASKFOPASOPFAJKAKAKAKAKAJAJAJAJAJAJ")

        textTitle = view.findViewById(R.id.room_text_title)
        buttonDesc = view.findViewById(R.id.room_button_description)

        usersRecyclerView = view.findViewById(R.id.recycler_view)
        usersRecyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
        usersRecyclerView.setHasFixedSize(false)

        viewModel.response.observe(viewLifecycleOwner) { response ->
            response as RoomData
            if(response.residents != null) {
                usersRecyclerView.adapter = UserPreviewRecyclerAdapter(
                    response.residents!!,
                    requireContext()
                )
            }

            textTitle.text = response.rid.toString()

            buttonDesc.text = response.group

            buttonDesc.setOnClickListener{
                val map = mutableMapOf(Pair("Group", arrayListOf(response.group!!)))

                (context as MainActivity).addFragment(MyApplication.usersFragment(map))
            }

        }

    }

}