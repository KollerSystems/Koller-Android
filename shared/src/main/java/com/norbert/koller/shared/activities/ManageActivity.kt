package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.button.MaterialButton
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ActivityToolbarBinding
import com.norbert.koller.shared.databinding.ViewManageBarBinding

abstract class ManageActivity : ToolbarActivity(){

    companion object{
        fun displayButton(button: MaterialButton, text: String, icon : Int){
            button.text = text
            button.setIconResource(icon)
            button.setPaddingRelative(button.resources.getDimensionPixelSize(R.dimen.icon_button_padding), button.paddingTop, button.paddingEnd, button.paddingBottom)
        }
    }

    lateinit var manageBarBinding : ViewManageBarBinding

    override fun setContentView(view: View?) {

        manageBarBinding = ViewManageBarBinding.inflate(layoutInflater, null, false)
        manageBarBinding.root.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT, Gravity.BOTTOM)
        val frameLayout = FrameLayout(this)
        val layoutParams = MarginLayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.header_footer_size)
        view!!.layoutParams = layoutParams
        frameLayout.addView(view)
        frameLayout.addView(manageBarBinding.root)

        super.setContentView(frameLayout)
    }

    fun displayButton(text : String){
        manageBarBinding.button.text = text
    }

    fun displayButton(text: String, icon : Int){
        displayButton(text)
        manageBarBinding.button.icon = AppCompatResources.getDrawable(this, icon)
        manageBarBinding.button.setPaddingRelative(resources.getDimensionPixelSize(R.dimen.icon_button_padding), manageBarBinding.button.paddingTop, manageBarBinding.button.paddingEnd, manageBarBinding.button.paddingBottom)
    }

}