package com.norbert.koller.shared.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ButtonCardBinding
import com.norbert.koller.shared.databinding.ToggleCardBinding
import com.norbert.koller.shared.managers.setVisibilityBy

class CardButton(context: Context, attrs: AttributeSet) : Card(context, attrs) {

    lateinit var binding : ButtonCardBinding

    override fun getImageIcon(): ImageView {
        return binding.imageIcon
    }

    override fun getTextTitle(): TextView {
        return binding.textTitle
    }

    override fun getTextDescription(): TextView {
        return binding.textDescription
    }

    override fun inflate() {
        binding = ButtonCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()


        if(mEndIcon!=null) {
            binding.flEnd.background = mEndIcon
            binding.flEnd.visibility = VISIBLE
        }

        if (binding.textBadge.text == "0") {
            if (mEndIcon == null){
                binding.flEnd.visibility = GONE
            }
            binding.textBadge.visibility = GONE
        } else {
            binding.flEnd.visibility = VISIBLE
            binding.textBadge.visibility = VISIBLE
            binding.textBadge.text = mBadge.toString()
        }

    }

}