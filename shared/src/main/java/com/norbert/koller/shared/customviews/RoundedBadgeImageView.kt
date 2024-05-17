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



    private var mStrokeWidth: Int = 0
    val image : ImageView

    private val red : Int = 10
    private val yellow : Int = 12

    fun setUser(userData : com.norbert.koller.shared.data.UserData){

        Picasso.get()
            .load(userData.picture)
            .noPlaceholder()
            .into(image)


        setColorBasedOnClass(userData)
    }

    private fun setColorBasedOnClass(userData : com.norbert.koller.shared.data.UserData){

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

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.roundedBadgeImageView,
            0, 0
        )

        try {
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.roundedBadgeImageView_stroke_width, context.resources.getDimensionPixelSize(R.dimen.text_container_half_margin))
        } finally {
            typedArray.recycle()
        }


        View.inflate(context, R.layout.view_rounded_badge_image, this)

        radius = 99999f
        strokeColor = Color.BLACK

        setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorPrimaryContainer))







        image = findViewById(R.id.image)

        val mStrokeWidthInt = mStrokeWidth
        setContentPadding(mStrokeWidthInt,mStrokeWidthInt,mStrokeWidthInt,mStrokeWidthInt)
        strokeWidth = mStrokeWidthInt
    }

}