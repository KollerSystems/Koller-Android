package com.norbert.koller.shared.fragments

import android.Manifest
import android.R.attr.fragment
import android.R.attr.key
import android.R.attr.value
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
import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class UserFragment : Fragment() {

    var UID : Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            UID = bundle.getInt("UID")
        }



        val textName : TextView = view.findViewById(R.id.user_text_name)
        val buttonGroup : Button = view.findViewById(R.id.user_button_group)
        val buttonRoom : Button = view.findViewById(R.id.user_button_room)
        val buttonClass : Button = view.findViewById(R.id.user_button_class)
        val badgeUser : RoundedBadgeImageView = view.findViewById(R.id.badge_user)

        val discordCard : Chip = view.findViewById(R.id.user_view_discord)
        val facebookCard : Chip = view.findViewById(R.id.user_view_facebook)
        val instagramCard : Chip = view.findViewById(R.id.user_view_instagram)
        val emailCard : Chip = view.findViewById(R.id.user_view_email)

        val loadingOl : FullScreenLoading = view.findViewById(R.id.loading_overlay)

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

        fun loadData(){

            val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
            usersResponse.getUser(UID, APIInterface.getHeaderMap()).enqueue(
                object : Callback<UserData> {
                    override fun onResponse(
                        call: Call<UserData>,
                        userResponse: Response<UserData>
                    ) {
                        if (userResponse.code() == 200) {

                            val userData: UserData = userResponse.body()!!

                            badgeUser.setColorBasedOnClass(userData.Class?.Class)

                            textName.text = userData.Name

                            buttonGroup.text = userData.Group
                            buttonRoom.text = userData.RID.toString()
                            buttonClass.text = userData.Class?.Class

                            nestedScrollView.setOnScrollChangeListener{ view: View, i: Int, i1: Int, i2: Int, i3: Int ->
                                if (isVisible(textName)) {
                                    (activity as MainActivity).setToolbarTitle(getString(R.string.user),null)

                                } else {
                                    (activity as MainActivity).setToolbarTitle(userData.Name, userData.createDescription())
                                }
                            }

                            buttonRoom.setOnClickListener{
                                val bundle = Bundle()
                                bundle.putInt("RID", userData.RID!!)
                                val fragment = MyApplication.roomFragment()
                                fragment.arguments = bundle
                                (requireContext() as MainActivity).changeFragment(fragment)
                            }

                            showAndSetIfNotNull(discordCard, userData.Discord)
                            showAndSetIfNotNull(facebookCard, userData.Facebook)
                            showAndSetIfNotNull(instagramCard, userData.Instagram)
                            showAndSetIfNotNull(emailCard, userData.Email)

                            badgeUser.card.setOnClickListener{
                                StfalconImageViewer.Builder(context, listOf(badgeUser.image.drawable)){view, drawable ->
                                    view.setImageDrawable(drawable)
                                }
                                    .withStartPosition(0)
                                    .withTransitionFrom(badgeUser.image)
                                    .show(parentFragmentManager)
                            }

                            loadingOl.setState(FullScreenLoading.NONE)
                            sendNotification()

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

        loadingOl.loadData = {loadData()}
    }



    private val notificationId = 101
    private fun sendNotification() {

        // Create an explicit intent for an Activity in your app.
        val intent = Intent(requireContext(), MyNotificationPublisher::class.java)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val ACTION_SNOOZE = "snooze"

        val snoozeIntent = Intent(requireContext(), MyApplication.openMain::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)


        val builder = NotificationCompat.Builder(requireContext(), NotificationHelper.OCCUPATION_REMINDER_CHANNEL)
            .setSmallIcon(R.drawable.notifications)
            .setContentTitle("Hello")
            .setContentText("This is a sample notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(R.drawable.eye, "ÚGY VAN!!", snoozePendingIntent)



        with(NotificationManagerCompat.from(requireContext())) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notify(notificationId, builder.build())
            }
        }




    }

}