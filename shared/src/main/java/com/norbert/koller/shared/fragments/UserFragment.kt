package com.norbert.koller.shared.fragments

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.transition.MaterialContainerTransform
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.FullScreenLoading
import com.norbert.koller.shared.customviews.RoundedBadgeImageView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.setVisibilityBy
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.skydoves.androidveil.VeilLayout
import com.stfalcon.imageviewer.StfalconImageViewer
import com.stfalcon.imageviewer.common.extensions.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class UserFragment(uid : Int? = null) : DetailsFragment(uid) {

    lateinit var scbStatus : SimpleCardButton
    lateinit var textStatus : TextView

    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }

    override fun getDataTag(): String {
        return "user"
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return {RetrofitInstance.api.getUser(viewModel.id!!)}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)



        val textName : TextView = view.findViewById(R.id.user_text_name)
        val buttonGroup : Button = view.findViewById(R.id.user_button_group)
        val buttonRoom : Button = view.findViewById(R.id.user_button_room)
        val buttonClass : Button = view.findViewById(R.id.user_button_class)
        val badgeUser : RoundedBadgeImageView = view.findViewById(R.id.badge_user)

        val discordChip : Chip = view.findViewById(R.id.user_view_discord)
        val facebookChip : Chip = view.findViewById(R.id.user_view_facebook)
        val instagramChip : Chip = view.findViewById(R.id.user_view_instagram)
        val emailChip : Chip = view.findViewById(R.id.user_view_email)

        val NestedScrollView : NestedScrollView = view.findViewById(R.id.nested_scroll_view)

        scbStatus = view.findViewById(R.id.scb_status)
        textStatus = view.findViewById(R.id.text_status)


        scbStatus.imageViewIcon!!.visibility = VISIBLE

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
                chip.text = string
                chip.setOnClickListener{
                    ApplicationManager.setClipboard(requireContext(), string)
                }
            }
        }

        viewModel.response.observe(viewLifecycleOwner) {response ->


            response as UserData

            badgeUser.setUser(response)

            textName.text = response.name


            buttonRoom.text = response.rid.toString()
            buttonGroup.text = response.group?.group
            buttonClass.text = response.class_?.class_



            buttonRoom.setOnClickListener {
                (requireContext() as MainActivity).addFragment(ApplicationManager.roomFragment(response.rid!!))
            }

            buttonGroup.setOnClickListener {
                val userFragment = ApplicationManager.usersFragment(null)
                    .setFilter("Group.ID", response.group!!.id.toString())
                (requireContext() as MainActivity).addFragment(userFragment)
            }

            buttonClass.setOnClickListener {
                val userFragment = ApplicationManager.usersFragment(null)
                    .setFilter("Class.ID", response.class_!!.id.toString())
                (requireContext() as MainActivity).addFragment(userFragment)
            }

            NestedScrollView.setOnScrollChangeListener { _: View, _: Int, _: Int, _: Int, _: Int ->
                if (isVisible(textName)) {
                    (activity as MainActivity).setToolbarTitle(getString(R.string.user))

                } else {
                    (activity as MainActivity).setToolbarTitle(response.name, response.createDescription())
                }
            }

            if(response.contacts != null){
                showAndSetIfNotNull(discordChip, response.contacts.discord)
                showAndSetIfNotNull(facebookChip, response.contacts.facebook)
                showAndSetIfNotNull(instagramChip, response.contacts.instagram)
            }

        }

        badgeUser.setOnClickListener{
            StfalconImageViewer.Builder(context, listOf(badgeUser.image.drawable)){view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withStartPosition(0)
                .withTransitionFrom(badgeUser.image)
                .show(parentFragmentManager)
        }
    }
}