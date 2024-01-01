package com.norbert.koller.shared.fragments

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.FullScreenLoading
import com.norbert.koller.shared.customviews.RoundedBadgeImageView
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class UserFragment(val uid : Int? = null) : Fragment() {

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

        val discordChip : Chip = view.findViewById(R.id.user_view_discord)
        val facebookChip : Chip = view.findViewById(R.id.user_view_facebook)
        val instagramChip : Chip = view.findViewById(R.id.user_view_instagram)
        val emailChip : Chip = view.findViewById(R.id.user_view_email)

        loadingOl = view.findViewById(R.id.loading_overlay)

        val NestedScrollView : NestedScrollView = view.findViewById(R.id.nested_scroll_view)

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
            viewModel.id = uid!!
            loadingOl.loadData = {loadData()}
        }

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as UserData

            badgeUser.setColorBasedOnClass(response.class_?.class_)

            textName.text = response.name

            buttonRoom.text = response.rid.toString()
            buttonGroup.text = response.group
            buttonClass.text = response.class_?.class_

            buttonRoom.setOnClickListener {
                (requireContext() as MainActivity).addFragment(MyApplication.roomFragment(response.rid!!))
            }

            buttonGroup.setOnClickListener {
                (requireContext() as MainActivity).addFragment(MyApplication.usersFragment(mutableMapOf(Pair("Group", arrayListOf(response.group!!)))))
            }

            buttonClass.setOnClickListener {
                (requireContext() as MainActivity).addFragment(MyApplication.usersFragment(mutableMapOf(Pair("Class", arrayListOf(response.class_.toString())))))
            }

            NestedScrollView.setOnScrollChangeListener { _: View, _: Int, _: Int, _: Int, _: Int ->
                if (isVisible(textName)) {
                    (activity as MainActivity).setToolbarTitle(getString(R.string.user), null)

                } else {
                    (activity as MainActivity).setToolbarTitle(response.name, response.createDescription())
                }
            }

            showAndSetIfNotNull(discordChip, response.discord)
            showAndSetIfNotNull(facebookChip, response.facebook)
            showAndSetIfNotNull(instagramChip, response.instagram)
            showAndSetIfNotNull(emailChip, response.email)
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

        RetrofitInstance.communicate(lifecycleScope, {RetrofitInstance.api.getUser(viewModel.id)},
            {
                viewModel.response.value = it as UserData
                loadingOl.setState(FullScreenLoading.NONE)
            },
            {errorMsg, errorBody ->
                loadingOl.setState(FullScreenLoading.ERROR)
            })

    }
}