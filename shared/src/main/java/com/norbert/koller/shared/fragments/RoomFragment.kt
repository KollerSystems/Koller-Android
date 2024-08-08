package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.databinding.ContentFragmentRoomHeaderBinding
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.recycleradapters.UserPreviewRecyclerAdapter
import retrofit2.Response


abstract class RoomFragment(rid: Int? = null) : DetailsFragment(rid) {

    override fun getFragmentTitle(): String? {
        return getString(R.string.room)
    }

    abstract fun getHeaderBinding() : ContentFragmentRoomHeaderBinding

    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }

    override fun getDataTag(): String {
        return "room"
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return {RetrofitInstance.api.getRoom(viewModel.id!!)}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getHeaderBinding().recyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
        getHeaderBinding().recyclerView.setHasFixedSize(false)

        viewModel.response.observe(viewLifecycleOwner) { response ->
            response as RoomData
            if(response.residents != null) {
                getHeaderBinding().recyclerView.adapter = UserPreviewRecyclerAdapter(
                    response.residents!!,
                    requireContext()
                )
            }

            getHeaderBinding().textTitle.text = response.rid.toString()

            getHeaderBinding().btnDescription.text = response.group?.group

            getHeaderBinding().btnDescription.setOnClickListener{

                val userFragment = ApplicationManager.roomsFragment(null)
                    .setFilter("Group.ID", response.group!!.id.toString())

                (context as MainActivity).addFragment(userFragment)
            }
        }
    }
}