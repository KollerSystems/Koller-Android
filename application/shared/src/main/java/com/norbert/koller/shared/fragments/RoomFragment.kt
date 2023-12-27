package com.norbert.koller.shared.fragments

import android.R.attr.key
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitHelper
import com.norbert.koller.shared.customview.FullScreenLoading
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapter.UserPreviewRecyclerAdapter
import com.norbert.koller.shared.viewmodels.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class RoomFragment : Fragment() {

    lateinit var usersRecyclerView: RecyclerView
    lateinit var userDataArrayList: ArrayList<UserData>
    lateinit var textTitle : TextView
    lateinit var buttonDesc : Button
    lateinit var loadingOl : FullScreenLoading

    private lateinit var viewModel: UserViewModel
    var RID : Int = -1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            RID = bundle.getInt("RID")
        }
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]


        textTitle = view.findViewById(R.id.room_text_title)
        buttonDesc = view.findViewById(R.id.room_button_description)
        loadingOl = view.findViewById(R.id.loading_overlay)

        usersRecyclerView = view.findViewById(R.id.recycler_view)
        usersRecyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false)
        usersRecyclerView.setHasFixedSize(false)



        if(!viewModel.response.isInitialized){
            loadingOl.loadData = {loadData()}
        }

        viewModel.response.observe(viewLifecycleOwner) { response ->
            response as RoomData
            if(response.Residents != null) {
                usersRecyclerView.adapter = UserPreviewRecyclerAdapter(
                    response.Residents!!,
                    requireContext()
                )
            }

            textTitle.text = response.RID.toString()

            buttonDesc.text = response.Group

            buttonDesc.setOnClickListener{
                (context as MainActivity).addFragment(MyApplication.usersFragment())
            }

        }

    }

    fun loadData(){
        val roomResponse = RetrofitHelper.buildService(APIInterface::class.java)
        roomResponse.getRoom(RID, APIInterface.getHeaderMap()).enqueue(
            object : Callback<RoomData> {
                override fun onResponse(
                    call: Call<RoomData>,
                    userResponse: Response<RoomData>
                ) {
                    if (userResponse.isSuccessful) {

                        viewModel.response.value = userResponse.body()!!
                        loadingOl.setState(FullScreenLoading.NONE)

                    } else {
                        loadingOl.setState(FullScreenLoading.ERROR)
                    }
                }

                override fun onFailure(call: Call<RoomData>, t: Throwable) {
                    loadingOl.setState(FullScreenLoading.ERROR)
                }
            }
        )
    }

}