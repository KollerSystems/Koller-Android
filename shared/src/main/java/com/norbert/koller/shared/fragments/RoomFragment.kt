package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.FilterConfigData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.databinding.ContentFragmentRoomHeaderBinding
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.recycleradapters.UserPreviewRecyclerAdapter
import retrofit2.Response


abstract class RoomFragment() : DetailsFragment() {

    override fun getDataType(): Class<*> {
        return RoomData::class.java
    }

    override fun getFragmentTitle(): String? {
        return getString(R.string.room)
    }

    abstract fun getHeaderBinding() : ContentFragmentRoomHeaderBinding

    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return {RetrofitInstance.api.getRoom(viewModel.id!!)}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getHeaderBinding().recyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
        getHeaderBinding().recyclerView.setHasFixedSize(false)

        getHeaderBinding().cbRoomTidiness.setOnClickListener{
            getMainActivity().addFragment(ApplicationManager.roomTidinessListFragment())
        }

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

                val userFragment = ApplicationManager.roomListFragment()
                val bundle = Bundle()
                bundle.putParcelable("filters", FilterConfigData(mutableMapOf(Pair("Group.ID", mutableSetOf(response.group!!.id)))))
                userFragment.arguments = bundle

                (context as MainActivity).addFragment(userFragment)
            }
        }
    }
}