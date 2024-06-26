package com.norbert.koller.shared.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.RoundedBadgeImageView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.stfalcon.imageviewer.StfalconImageViewer
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
        val buttonGroup : Button = view.findViewById(R.id.button_group)
        val buttonRoom : Button = view.findViewById(R.id.button_room)
        val buttonClass : Button = view.findViewById(R.id.button_class_or_profession)
        val badgeUser : RoundedBadgeImageView = view.findViewById(R.id.badge_user)

        val discordChip : Chip = view.findViewById(R.id.user_view_discord)
        val facebookChip : Chip = view.findViewById(R.id.user_view_facebook)
        val instagramChip : Chip = view.findViewById(R.id.user_view_instagram)
        val emailChip : Chip = view.findViewById(R.id.user_view_email)
        val phoneChip : Chip = view.findViewById(R.id.user_view_phone)

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


        fun showAndSetIfNotNull(chip : Chip, any : Any?) : Boolean{
            if (any != null && any.toString().isNotBlank()) {
                chip.visibility = VISIBLE
                chip.text = any.toString()
                return true
            }
            return false
        }

        fun copyTextOnLongClick(chip : Chip){
            chip.setOnLongClickListener{

                ApplicationManager.setClipboard(requireContext(), chip.text.toString())
                return@setOnLongClickListener true
            }
        }

        fun openUserPageOnClick(packageName : String, url : String, chip : Chip){
            chip.setOnClickListener {

                val uri = Uri.parse("http://${url}/${chip.text}")
                val likeIng = Intent(Intent.ACTION_VIEW, uri)

                likeIng.setPackage(packageName)

                try {
                    startActivity(likeIng)
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://${url}/${chip.text}")
                        )
                    )
                }
            }

            copyTextOnLongClick(chip)
        }

        fun openInstagramOnClick(){
            openUserPageOnClick("com.instagram.android", "instagram.com/_u", instagramChip)
        }

        fun openFacebookOnClick(){
            openUserPageOnClick("com.facebook.katana", "facebook.com", facebookChip)
        }

        fun openEmailOnClick(){
            emailChip.setOnClickListener{
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:${emailChip.text}")
                startActivity(intent)
            }
            copyTextOnLongClick(emailChip)
        }

        fun openPhoneOnClick(){
            phoneChip.setOnClickListener{
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:${phoneChip.text}"))
                startActivity(intent)
            }
            copyTextOnLongClick(phoneChip)
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
                if(showAndSetIfNotNull(discordChip, response.contacts.discord)){
                    discordChip.setOnClickListener{
                        ApplicationManager.setClipboard(requireContext(), discordChip.text.toString())
                    }
                }

                if(showAndSetIfNotNull(facebookChip, response.contacts.facebook)){
                    openFacebookOnClick()
                }

                if(showAndSetIfNotNull(instagramChip, response.contacts.instagram)){
                    openInstagramOnClick()
                }

                if(showAndSetIfNotNull(phoneChip, response.contacts.phone)){
                    openPhoneOnClick()
                }

                if (!response.contacts.email.isNullOrBlank()) {
                    emailChip.text = response.contacts.email
                    openEmailOnClick()
                }
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