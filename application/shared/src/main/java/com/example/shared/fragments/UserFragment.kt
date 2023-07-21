package com.example.shared.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.activities.MainActivity
import com.example.shared.api.APIInterface
import com.example.shared.api.RetrofitHelper
import com.example.shared.data.UserData
import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class UserFragment : Fragment() {

    companion object {
        var userToGet : Int = -1
    }

    abstract fun showAndSetIfNotNull(card : View, string : String?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var textName : TextView = view.findViewById(R.id.user_text_name)
        var textDescription : TextView = view.findViewById(R.id.user_text_description)
        var imagePfp : ImageView = view.findViewById(R.id.user_image_pfp)

        var discordCard : TextView = view.findViewById(R.id.user_view_discord)
        var facebookCard : TextView = view.findViewById(R.id.user_view_facebook)
        var instagramCard : TextView = view.findViewById(R.id.user_view_instagram)
        var emailCard : TextView = view.findViewById(R.id.user_view_email)

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




        nestedScrollView.setOnScrollChangeListener{ view: View, i: Int, i1: Int, i2: Int, i3: Int ->
            if (isVisible(textName)) {
                (activity as MainActivity).setToolbarTitle(getString(R.string.user))

            } else {
                (activity as MainActivity).setToolbarTitle(textName.text)
            }
        }



        val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
        usersResponse.getUser(userToGet, APIInterface.getHeaderMap()).enqueue(
            object : Callback<UserData> {
                override fun onResponse(
                    call: Call<UserData>,
                    userResponse: Response<UserData>
                ) {
                    if (userResponse.code() == 200) {

                        var userData: UserData = userResponse.body()!!
                        textName.text = userData.Name

                        textDescription.text = MyApplication.createUserDescription(userData)

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
                        APIInterface.ServerErrorPopup(context)
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    APIInterface.ServerErrorPopup(context)
                }
            }
        )
    }

}