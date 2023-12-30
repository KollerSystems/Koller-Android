package com.norbert.koller.shared.fragments

import android.Manifest
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.MyNotificationPublisher
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitHelper
import com.norbert.koller.shared.customview.FullScreenLoading
import com.norbert.koller.shared.customview.RoundedBadgeImageView
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.helpers.NotificationHelper
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class UserFragment(val UID : Int? = null) : Fragment() {

    lateinit var loadingOl : FullScreenLoading

    lateinit var viewModel: ResponseViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]



        val textName : TextView = view.findViewById(R.id.user_text_name)
        val buttonGroup : Button = view.findViewById(R.id.user_button_group)
        val buttonRoom : Button = view.findViewById(R.id.user_button_room)
        val buttonClass : Button = view.findViewById(R.id.user_button_class)
        val badgeUser : RoundedBadgeImageView = view.findViewById(R.id.badge_user)

        val discordCard : Chip = view.findViewById(R.id.user_view_discord)
        val facebookCard : Chip = view.findViewById(R.id.user_view_facebook)
        val instagramCard : Chip = view.findViewById(R.id.user_view_instagram)
        val emailCard : Chip = view.findViewById(R.id.user_view_email)

        loadingOl = view.findViewById(R.id.loading_overlay)

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

        if(!viewModel.response.isInitialized){
            viewModel.ID = UID!!
            loadingOl.loadData = {loadData()}
        }

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as UserData

            badgeUser.setColorBasedOnClass(response.Class?.Class)

            textName.text = response.Name

            buttonGroup.text = response.Group
            buttonRoom.text = response.RID.toString()
            buttonClass.text = response.Class?.Class

            nestedScrollView.setOnScrollChangeListener { _: View, _: Int, _: Int, _: Int, _: Int ->
                if (isVisible(textName)) {
                    (activity as MainActivity).setToolbarTitle(getString(R.string.user), null)

                } else {
                    (activity as MainActivity).setToolbarTitle(response.Name, response.createDescription())
                }
            }

            buttonRoom.setOnClickListener {
                (requireContext() as MainActivity).addFragment(MyApplication.roomFragment(response.RID!!))
            }

            showAndSetIfNotNull(discordCard, response.Discord)
            showAndSetIfNotNull(facebookCard, response.Facebook)
            showAndSetIfNotNull(instagramCard, response.Instagram)
            showAndSetIfNotNull(emailCard, response.Email)
        }

        badgeUser.card.setOnClickListener{
            StfalconImageViewer.Builder(context, listOf(badgeUser.image.drawable)){view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withStartPosition(0)
                .withTransitionFrom(badgeUser.image)
                .show(parentFragmentManager)
        }
    }

    fun loadData(){

        val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
        usersResponse.getUser(viewModel.ID, APIInterface.getHeaderMap()).enqueue(
            object : Callback<UserData> {
                override fun onResponse(
                    call: Call<UserData>,
                    userResponse: Response<UserData>
                ) {
                    if (userResponse.code() == 200) {


                        viewModel.response.value = userResponse.body()!!
                        loadingOl.setState(FullScreenLoading.NONE)


                    } else {
                        loadingOl.setState(FullScreenLoading.ERROR)
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    loadingOl.setState(FullScreenLoading.ERROR)
                }
            }
        )
    }
}