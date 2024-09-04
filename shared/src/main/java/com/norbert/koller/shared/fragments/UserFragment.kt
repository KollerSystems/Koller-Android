package com.norbert.koller.shared.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import androidx.core.widget.NestedScrollView
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ContentFragmentUserHeaderBinding
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.stfalcon.imageviewer.StfalconImageViewer
import retrofit2.Response


abstract class UserFragment(uid : Int? = null) : DetailsFragment(uid) {

    abstract fun getHeaderBinding() : ContentFragmentUserHeaderBinding
    abstract fun getNestedScrollView() : NestedScrollView

    override fun getDataType(): Class<*> {
        return UserData::class.java
    }

    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }

    override fun getDataTag(): String {
        return "user"
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return {RetrofitInstance.api.getUser(viewModel.id!!)}
    }

    fun isVisible(view: View): Boolean {
        if (!view.isShown) {
            return false
        }
        val actualPosition = Rect()
        view.getGlobalVisibleRect(actualPosition)
        val screen = Rect(0, 0, Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels)
        return actualPosition.intersect(screen)
    }

    fun checkVisibility(){
        if (isVisible(getHeaderBinding().textName)) {
            (activity as MainActivity).setToolbarTitle(getString(R.string.user))

        } else {
            (activity as MainActivity).setToolbarTitle((viewModel.response.value as UserData).name, (viewModel.response.value as UserData).createDescription())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        getHeaderBinding().cbStatus.getImageIcon().visibility = VISIBLE

        getHeaderBinding().user.post{
            checkVisibility()
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
            openUserPageOnClick("com.instagram.android", "instagram.com/_u", getHeaderBinding().chipInstagram)
        }

        fun openFacebookOnClick(){
            openUserPageOnClick("com.facebook.katana", "facebook.com", getHeaderBinding().chipFacebook)
        }

        fun openEmailOnClick(){
            getHeaderBinding().chipEmail.setOnClickListener{
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:${getHeaderBinding().chipEmail.text}")
                startActivity(intent)
            }
            copyTextOnLongClick(getHeaderBinding().chipEmail)
        }

        fun openPhoneOnClick(){
            getHeaderBinding().chipPhone.setOnClickListener{
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:${getHeaderBinding().chipPhone.text}"))
                startActivity(intent)
            }
            copyTextOnLongClick(getHeaderBinding().chipPhone)
        }

        viewModel.response.observe(viewLifecycleOwner) {response ->


            response as UserData

            getHeaderBinding().user.setUser(response)

            getHeaderBinding().textName.text = response.name


            getHeaderBinding().btnRoom.text = response.rid.toString()
            getHeaderBinding().btnGroup.text = response.group?.group
            getHeaderBinding().btnClassOrProfession.text = response.class_?.class_



            getHeaderBinding().btnRoom.setOnClickListener {
                (requireContext() as MainActivity).addFragment(ApplicationManager.roomFragment(response.rid!!))
            }

            getHeaderBinding().btnGroup.setOnClickListener {
                val userFragment = ApplicationManager.userListFragment(null)
                    .setFilter("Group.ID", response.group!!.id.toString())
                (requireContext() as MainActivity).addFragment(userFragment)
            }

            getHeaderBinding().btnClassOrProfession.setOnClickListener {
                val userFragment = ApplicationManager.userListFragment(null)
                    .setFilter("Class.ID", response.class_!!.id.toString())
                (requireContext() as MainActivity).addFragment(userFragment)
            }

            getNestedScrollView().setOnScrollChangeListener { _: View, _: Int, _: Int, _: Int, _: Int ->
                checkVisibility()
            }

            if(response.contacts != null){
                if(showAndSetIfNotNull(getHeaderBinding().chipDiscord, response.contacts.discord)){
                    getHeaderBinding().chipDiscord.setOnClickListener{
                        ApplicationManager.setClipboard(requireContext(), getHeaderBinding().chipDiscord.text.toString())
                    }
                }

                if(showAndSetIfNotNull(getHeaderBinding().chipFacebook, response.contacts.facebook)){
                    openFacebookOnClick()
                }

                if(showAndSetIfNotNull(getHeaderBinding().chipInstagram, response.contacts.instagram)){
                    openInstagramOnClick()
                }

                if(showAndSetIfNotNull(getHeaderBinding().chipPhone, response.contacts.phone)){
                    openPhoneOnClick()
                }

                if (!response.contacts.email.isNullOrBlank()) {
                    getHeaderBinding().chipEmail.text = response.contacts.email
                    openEmailOnClick()
                }
            }

        }

        getHeaderBinding().user.setOnClickListener{
            StfalconImageViewer.Builder(context, listOf(getHeaderBinding().user.getImage().drawable)){view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withStartPosition(0)
                .withTransitionFrom(getHeaderBinding().user.getImage())
                .show(parentFragmentManager)
        }
    }
}