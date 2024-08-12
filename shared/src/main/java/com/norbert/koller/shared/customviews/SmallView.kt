package com.norbert.koller.shared.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ButtonCardBinding
import com.norbert.koller.shared.databinding.ViewSmallBinding
import com.norbert.koller.shared.managers.setVisibilityBy

class SmallView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var mText: String = ""
    private var mDesc: String = ""
    private var mIcon: Drawable? = null

    private var binding : ViewSmallBinding

    fun getImageIcon() : ImageView{
        return binding.imageIcon
    }
    fun getTextTitle() : TextView{
        return binding.textTitle
    }
    fun getTextDescription() : TextView{
        return binding.textDescription
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.simpleCardButton,
            0, 0
        )

        try {
            mText = typedArray.getString(R.styleable.simpleCardButton_text) ?: ""
            mDesc = typedArray.getString(R.styleable.simpleCardButton_description) ?: ""
            mIcon = typedArray.getDrawable(R.styleable.simpleCardButton_icon)
        } finally {
            typedArray.recycle()
        }

        binding = ViewSmallBinding.inflate(LayoutInflater.from(context), this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        getImageIcon().setImageDrawable(mIcon)
        getTextTitle().text = mText
        getTextDescription().text = mDesc
    }
}