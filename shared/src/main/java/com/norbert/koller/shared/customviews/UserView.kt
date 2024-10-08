package com.norbert.koller.shared.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.github.iielse.imageviewer.ImageViewerBuilder
import com.github.iielse.imageviewer.core.Photo
import com.github.iielse.imageviewer.core.SimpleDataProvider
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ViewUserBinding
import com.norbert.koller.shared.managers.getAttributeColor
import com.squareup.picasso.Picasso

class UserView(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private val binding = ViewUserBinding.inflate(LayoutInflater.from(context), this, true)

    fun getImage() : ImageView{
        return binding.image
    }

    private var mStrokeWidth: Int = 0

    private val red : Int = 10
    private val yellow : Int = 12

    fun setUser(userData : UserData){

        Picasso.get()
            .load(userData.picture)
            .noPlaceholder()
            .into(binding.image)


        setColorBasedOnClass(userData)
    }

    private fun setColorBasedOnClass(userData : UserData){

        if(userData.role == 2){
            strokeColor = context.getAttributeColor(R.attr.colorBlue)
            return
        }

        val year : Int = userData.class_!!.class_.split(".")[0].toInt()

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
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.roundedBadgeImageView_stroke_width, context.resources.getDimensionPixelSize(R.dimen.outline_width))
        } finally {
            typedArray.recycle()
        }

        radius = 99999f
        strokeColor = Color.GRAY

        setCardBackgroundColor(Color.TRANSPARENT)


        val mStrokeWidthInt = mStrokeWidth
        setContentPadding(mStrokeWidthInt,mStrokeWidthInt,mStrokeWidthInt,mStrokeWidthInt)
        strokeWidth = mStrokeWidthInt
    }

}