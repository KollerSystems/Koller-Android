package com.example.koller

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView


class SimpleCardButton(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private var mText: String = ""
    private var mDesc: String = ""
    private var mIcon: Drawable? = null

    private val textText: TextView
    private val textDesc: TextView
    private val imageViewIcon: ImageView

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

        View.inflate(context, R.layout.simple_card_button, this)
        textText = findViewById(R.id.text_text)
        textDesc = findViewById(R.id.text_description)
        imageViewIcon = findViewById(R.id.iv_icon)

        this.radius = 0f
        this.outlineProvider = null

        val padding = resources.getDimensionPixelSize(R.dimen.panel_padding)

        setContentPadding(padding,padding,padding,padding)


        imageViewIcon.setImageDrawable(mIcon)

        textText.text = mText

        if (mDesc.isEmpty()) {
            textDesc.visibility = View.GONE
        } else {
            textDesc.text = mDesc
            textDesc.visibility = View.VISIBLE
        }
    }

}