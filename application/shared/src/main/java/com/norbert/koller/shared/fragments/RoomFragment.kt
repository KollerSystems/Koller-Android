package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.customview.FullScreenLoading
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitHelper
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapter.UserPreviewRecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class RoomFragment(val RID : Int) : Fragment() {

    lateinit var usersRecyclerView: RecyclerView
    lateinit var userDataArrayList: ArrayList<UserData>
    lateinit var textTitle : TextView
    lateinit var buttonDesc : Button
    lateinit var loadingOl : FullScreenLoading




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        textTitle = view.findViewById(R.id.room_text_title)
        buttonDesc = view.findViewById(R.id.room_button_description)
        loadingOl = view.findViewById(R.id.loading_overlay)

        usersRecyclerView = view.findViewById(R.id.recycler_view)
        usersRecyclerView.setHasFixedSize(false)

       fun loadData(){
           val roomResponse = RetrofitHelper.buildService(APIInterface::class.java)
           roomResponse.getRoom(RID, APIInterface.getHeaderMap()).enqueue(
               object : Callback<RoomData> {
                   override fun onResponse(
                       call: Call<RoomData>,
                       userResponse: Response<RoomData>
                   ) {
                       if (userResponse.isSuccessful) {

                           val roomData: RoomData = userResponse.body()!!


                           if(roomData.Residents != null) {
                               usersRecyclerView.adapter = UserPreviewRecyclerAdapter(
                                   roomData.Residents!!,
                                   requireContext()
                               )
                           }

                           textTitle.text = roomData.RID.toString()

                           buttonDesc.text = roomData.Group

                           buttonDesc.setOnClickListener{
                               (context as MainActivity).changeFragment(MyApplication.usersFragment())
                           }

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

        loadingOl.loadData = {loadData()}

    }

}