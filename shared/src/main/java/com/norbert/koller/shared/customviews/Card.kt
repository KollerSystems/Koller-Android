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
import com.norbert.koller.shared.managers.setVisibilityBy

abstract class Card(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private var mText: String = ""
    private var mRounded: Boolean = false
    private var mDesc: String = ""
    var mBadge: Int = 0
    private var mIcon: Drawable? = null
    var mEndIcon: Drawable? = null
    private var mSwapDescriptionAndText = false


    abstract fun getImageIcon() : ImageView
    abstract fun getTextTitle() : TextView
    abstract fun getTextDescription() : TextView

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.simpleCardButton,
            0, 0
        )

        try {
            mText = typedArray.getString(R.styleable.simpleCardButton_text) ?: ""
            mDesc = typedArray.getString(R.styleable.simpleCardButton_description) ?: ""
            mBadge = typedArray.getInt(R.styleable.simpleCardButton_badge, 0)
            mIcon = typedArray.getDrawable(R.styleable.simpleCardButton_icon)
            mEndIcon = typedArray.getDrawable(R.styleable.simpleCardButton_end_icon)

            mSwapDescriptionAndText = typedArray.getBoolean(R.styleable.simpleCardButton_swap_description_and_text, false)
            mRounded = typedArray.getBoolean(R.styleable.simpleCardButton_rounded, false)
        } finally {
            typedArray.recycle()
        }

        this.inflate()
        preventCornerOverlap = false
        val padding = resources.getDimensionPixelSize(R.dimen.card_padding)
        this.setContentPadding(padding,padding,padding,padding)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        getImageIcon().setVisibilityBy(mIcon)
        getImageIcon().setImageDrawable(mIcon)

        getTextTitle().text = mText

        if (mDesc.isEmpty()) {
            getTextDescription().visibility = View.GONE
        } else {
            getTextDescription().text = mDesc
            getTextDescription().visibility = View.VISIBLE

            if(!mSwapDescriptionAndText){
                getTextDescription().bringToFront()
            }
            else{
                getTextTitle().bringToFront()
            }
        }

    }

    abstract fun inflate()

}