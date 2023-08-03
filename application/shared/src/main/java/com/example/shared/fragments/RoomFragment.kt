package com.example.shared.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.activities.MainActivity
import com.example.shared.api.APIInterface
import com.example.shared.api.RetrofitHelper
import com.example.shared.data.RoomData
import com.example.shared.data.UserData
import com.example.shared.navigateWithDefaultAnimation
import com.example.shared.recycleradapter.UserPreviewRecyclerAdapter
import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class RoomFragment : Fragment() {

    companion object {
        var toGet : Int = -1

        fun open(context : Context, RID : Int){
            toGet = RID

            context as MainActivity
            context.bottomNavigationView.selectedItemId =R.id.studentHostelNest
            context.navController.navigateWithDefaultAnimation(R.id.roomFragment)
        }
    }

    lateinit var usersRecyclerView: RecyclerView
    lateinit var userDataArrayList: ArrayList<UserData>
    lateinit var textTitle : TextView
    lateinit var buttonDesc : Button
    lateinit var loadingOl : View


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        textTitle = view.findViewById(R.id.room_text_title)
        buttonDesc = view.findViewById(R.id.room_button_description)
        loadingOl = view.findViewById(R.id.loading_overlay)
        loadingOl.visibility = VISIBLE

        usersRecyclerView = view.findViewById(R.id.recycler_view)
        usersRecyclerView.setHasFixedSize(false)

        val roomResponse = RetrofitHelper.buildService(APIInterface::class.java)
        roomResponse.getRoom(toGet, APIInterface.getHeaderMap()).enqueue(
            object : Callback<RoomData> {
                override fun onResponse(
                    call: Call<RoomData>,
                    userResponse: Response<RoomData>
                ) {
                    if (userResponse.isSuccessful) {

                        val roomData: RoomData = userResponse.body()!!


                        if(roomData.residents != null) {
                            usersRecyclerView.adapter = UserPreviewRecyclerAdapter(
                                roomData.residents!!,
                                requireContext()
                            )
                        }

                        textTitle.text = roomData.RID.toString()

                        buttonDesc.text = roomData.Group

                        buttonDesc.setOnClickListener{
                            (context as MainActivity).navController.navigateWithDefaultAnimation(R.id.usersFragment)
                        }

                        loadingOl.animate()
                            .alpha(0.0f)
                            .setDuration(300)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    super.onAnimationEnd(animation)
                                    loadingOl.visibility = View.GONE
                                }
                            })

                    } else {
                        APIInterface.ServerErrorPopup(context)
                    }
                }

                override fun onFailure(call: Call<RoomData>, t: Throwable) {
                    APIInterface.ServerErrorPopup(context)
                }
            }
        )

    }

}