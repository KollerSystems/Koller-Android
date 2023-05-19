package com.example.koller.fragments

import APIInterface
import UserData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.example.koller.MyApplication
import com.example.koller.R
import com.example.koller.data.TodayData
import com.example.koller.recycleradapter.UserRecycleAdapter
import com.google.android.material.appbar.AppBarLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_user, container, false)

        var textName : TextView = view.findViewById(R.id.user_text_name)
        var textDescription : TextView = view.findViewById(R.id.user_text_description)
        var imagePfp : ImageView = view.findViewById(R.id.user_image_pfp)
        var discordName : TextView = view.findViewById(R.id.user_text_discord_name)
        var facebookName : TextView = view.findViewById(R.id.user_text_facebook_name)
        var instagramName : TextView = view.findViewById(R.id.user_text_instagram_name)
        var email : TextView = view.findViewById(R.id.user_text_email)

        val userID : String? = requireActivity().intent.extras?.getString("userID")
        if(userID != null){
            val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
            usersResponse.getUser(userID, APIInterface.getHeaderMap()).enqueue(
                object : Callback<UserData> {
                    override fun onResponse(
                        call: Call<UserData>,
                        userResponse: Response<UserData>
                    ) {
                        if (userResponse.code() == 200) {

                            var userData: UserData = userResponse.body()!!
                            textName.text = userData.Name
                            textDescription.text = MyApplication.createUserDescription(userData)


                        } else {
                            APIInterface.ServerErrorPopup(requireContext())
                        }
                    }

                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        APIInterface.ServerErrorPopup(requireContext())
                    }
                }
            )
        }


        val appBar : AppBarLayout = view.findViewById(R.id.appbar_user)
        val motionLayout : MotionLayout = view.findViewById(R.id.motion_layout)
        val listener = AppBarLayout.OnOffsetChangedListener{appBar, verticalOffset ->
            val seekPosition = -verticalOffset /appBar.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }
        appBar.addOnOffsetChangedListener(listener)



        return view
    }
}