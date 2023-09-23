package com.norbert.koller.shared.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitHelper
import com.norbert.koller.shared.data.UserData
import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class UserFragment(val UID : Int) : Fragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        val textName : TextView = view.findViewById(R.id.user_text_name)
        val buttonGroup : Button = view.findViewById(R.id.user_button_group)
        val buttonRoom : Button = view.findViewById(R.id.user_button_room)
        val buttonClass : Button = view.findViewById(R.id.user_button_class)
        val imagePfp : ImageView = view.findViewById(R.id.user_image_pfp)

        val discordCard : Chip = view.findViewById(R.id.user_view_discord)
        val facebookCard : Chip = view.findViewById(R.id.user_view_facebook)
        val instagramCard : Chip = view.findViewById(R.id.user_view_instagram)
        val emailCard : Chip = view.findViewById(R.id.user_view_email)

        val loadingOl : View = view.findViewById(R.id.loading_overlay)
        loadingOl.visibility = View.VISIBLE

        val nestedScrollView : NestedScrollView = view.findViewById(R.id.nested_scroll_view)

        fun isVisible(view: View): Boolean {
            if (!view.isShown) {
                return false
            }
            val actualPosition = Rect()
            view.getGlobalVisibleRect(actualPosition)
            val screen = Rect(0, 0, Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels)
            return actualPosition.intersect(screen)
        }



        fun showAndSetIfNotNull(chip : Chip, string : String?){
            if (!string.isNullOrBlank()) {
                chip.visibility = View.VISIBLE
                chip.setOnClickListener{
                    MyApplication.setClipboard(requireContext(), string)
                }
            }
        }




        val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
        usersResponse.getUser(UID, APIInterface.getHeaderMap()).enqueue(
            object : Callback<UserData> {
                override fun onResponse(
                    call: Call<UserData>,
                    userResponse: Response<UserData>
                ) {
                    if (userResponse.code() == 200) {

                        val userData: UserData = userResponse.body()!!
                        textName.text = userData.Name

                        buttonGroup.text = userData.Group
                        buttonRoom.text = userData.RID.toString()
                        buttonClass.text = userData.Class?.Class

                        nestedScrollView.setOnScrollChangeListener{ view: View, i: Int, i1: Int, i2: Int, i3: Int ->
                            if (isVisible(textName)) {
                                (activity as MainActivity).setToolbarTitle(getString(R.string.user),null)

                            } else {
                                (activity as MainActivity).setToolbarTitle(userData.Name,MyApplication.createUserDescription(userData))
                            }
                        }

                        buttonRoom.setOnClickListener{
                            (requireContext() as MainActivity).changeFragment(MyApplication.roomFragment(userData.RID!!))
                        }

                        showAndSetIfNotNull(discordCard, userData.Discord)
                        showAndSetIfNotNull(facebookCard, userData.Facebook)
                        showAndSetIfNotNull(instagramCard, userData.Instagram)
                        showAndSetIfNotNull(emailCard, userData.Email)

                        imagePfp.setOnClickListener{
                            StfalconImageViewer.Builder(context, listOf(imagePfp.drawable)) { view, drawable ->
                                view.setImageDrawable(drawable)

                            }
                                .withStartPosition(0)
                                .withTransitionFrom(imagePfp)
                                .show()
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
                        APIInterface.serverErrorPopup(context)
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    APIInterface.serverErrorPopup(context)
                }
            }
        )
    }

}