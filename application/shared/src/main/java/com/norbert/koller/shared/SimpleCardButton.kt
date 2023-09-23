package com.norbert.koller.shared

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView


open class SimpleCardButton(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private var mText: String = ""
    private var mRounded: Boolean = false
    private var mDesc: String = ""
    private var mBadge: Int = 0
    private var mIcon: Drawable? = null
    private var mSwapDescriptionAndText = false

    private val textText: TextView
    private val textDesc: TextView
    private val textBadge: TextView?
    private val imageViewIcon: ImageView?

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
            mSwapDescriptionAndText = typedArray.getBoolean(R.styleable.simpleCardButton_swap_description_and_text, false)
            mRounded = typedArray.getBoolean(R.styleable.simpleCardButton_rounded, false)
        } finally {
            typedArray.recycle()
        }

        this.inflate()

        textText = findViewById(R.id.text_text)
        textDesc = findViewById(R.id.text_description)
        textBadge = findViewById(R.id.badge)
        imageViewIcon = findViewById(R.id.iv_icon)

        if(!mRounded) {
            this.radius = 0f
        }
        this.outlineProvider = null

        val padding = resources.getDimensionPixelSize(R.dimen.panel_padding)

        this.setContentPadding(padding,padding,padding,padding)


        imageViewIcon?.setImageDrawable(mIcon)

        textText.text = mText

        if(textBadge!=null) {
            if (textBadge.text == "0") {
                textBadge.visibility = GONE
            } else {
                textBadge.visibility = VISIBLE
                textBadge.text = mBadge.toString()
            }
        }

        if (mDesc.isEmpty()) {
            textDesc.visibility = View.GONE
        } else {
            textDesc.text = mDesc
            textDesc.visibility = View.VISIBLE

            if(!mSwapDescriptionAndText){
                textDesc.bringToFront()
            }
            else{
                textText.bringToFront()
            }
        }

    }

    open fun inflate(){
        View.inflate(context, R.layout.simple_card_button, this)
    }

}