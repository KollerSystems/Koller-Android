package com.norbert.koller.shared.customviews

import android.content.Context
import android.graphics.Color
import android.service.autofill.UserData
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor
import com.squareup.picasso.Picasso

class RoundedBadgeImageView(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private var mStrokeWidth: Float = 0f
    lateinit var image : ImageView

    val red : Int = 10
    val yellow : Int = 12

    fun setUser(userData : com.norbert.koller.shared.data.UserData){

        Picasso.get()
            .load(userData.picture)
            .noPlaceholder()
            .into(image)


        setColorBasedOnClass(userData)
    }

    fun setColorBasedOnClass(userData : com.norbert.koller.shared.data.UserData){

        val class_ = userData.class_?.class_
        if(class_.isNullOrBlank()){
            strokeColor = if(userData.group == null) {
                Color.BLACK
            } else{
                context.getAttributeColor(R.attr.colorGreen)
            }
            return
        }

        val year : Int = class_.split(".")[0].toInt()

        if(year <= red){
            strokeColor = context.getAttributeColor(R.attr.colorRed)
        }
        else if(year <= yellow){
            strokeColor = context.getAttributeColor(R.attr.colorYellow)
        }
        else{
            strokeColor = context.getAttributeColor(R.attr.colorGreen)
        }
    }

    init {


        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        radius = 99999f
        strokeColor = Color.BLACK
        strokeWidth = context.resources.getDimensionPixelSize(R.dimen.text_container_margin)

        setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorPrimaryContainer))

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.roundedBadgeImageView,
            0, 0
        )

        try {
            mStrokeWidth = typedArray.getDimension(R.styleable.roundedBadgeImageView_stroke_width, ApplicationManager.convertDpToPixel(5, context).toFloat())
        } finally {
            typedArray.recycle()
        }


        View.inflate(context, R.layout.view_rounded_badge_image, this)

        image = findViewById(R.id.image)

        val mStrokeWidthInt = mStrokeWidth.toInt()
        setContentPadding(mStrokeWidthInt,mStrokeWidthInt,mStrokeWidthInt,mStrokeWidthInt)
        strokeWidth = mStrokeWidthInt
    }

}