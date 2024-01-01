package com.norbert.koller.shared.fragments

import android.os.Bundle
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
import com.norbert.koller.shared.recycleradapters.UserPreviewRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class RoomFragment(val rid: Int? = null) : Fragment() {

    lateinit var usersRecyclerView: RecyclerView
    lateinit var userDataArrayList: ArrayList<UserData>
    lateinit var textTitle : TextView
    lateinit var buttonDesc : Button
    lateinit var loadingOl : FullScreenLoading

    private lateinit var viewModel: ResponseViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]


        textTitle = view.findViewById(R.id.room_text_title)
        buttonDesc = view.findViewById(R.id.room_button_description)
        loadingOl = view.findViewById(R.id.loading_overlay)

        usersRecyclerView = view.findViewById(R.id.recycler_view)
        usersRecyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
        usersRecyclerView.setHasFixedSize(false)



        if(!viewModel.response.isInitialized){
            loadingOl.loadData = {loadData()}
            viewModel.id = rid!!
        }

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

    fun loadData(){

        RetrofitInstance.communicate(lifecycleScope, {RetrofitInstance.api.getRoom(viewModel.id)},
            {
                viewModel.response.value = it as RoomData
                loadingOl.setState(FullScreenLoading.NONE)
            },
            {errorMsg, errorBody ->
                loadingOl.setState(FullScreenLoading.ERROR)
            })
    }

}