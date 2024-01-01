package com.norbert.koller.shared.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor

class RoundedBadgeImageView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var mStrokeWidth: Float = 0f
    lateinit var image : ImageView
    lateinit var card : MaterialCardView

    val red : Int = 10
    val yellow : Int = 12

    fun setColorBasedOnClass(class_ : String?){
        if(class_.isNullOrBlank()){
            card.strokeColor = Color.BLACK
            return
        }

        val year : Int = class_.split(".")[0].toInt()

        if(year <= red){
            card.strokeColor = context.getAttributeColor(R.attr.colorRed)
        }
        else if(year <= yellow){
            card.strokeColor = context.getAttributeColor(R.attr.colorYellow)
        }
        else{
            card.strokeColor = context.getAttributeColor(R.attr.colorGreen)
        }
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.roundedBadgeImageView,
            0, 0
        )

        try {
            mStrokeWidth = typedArray.getDimension(R.styleable.roundedBadgeImageView_stroke_width, MyApplication.convertDpToPixel(5, context).toFloat())
        } finally {
            typedArray.recycle()
        }


        View.inflate(context, R.layout.view_rounded_badge_image, this)

        card  = findViewById(R.id.card)
        image = findViewById(R.id.image)

        val mStrokeWidthInt = mStrokeWidth.toInt()
        card.setContentPadding(mStrokeWidthInt,mStrokeWidthInt,mStrokeWidthInt,mStrokeWidthInt)
        card.strokeWidth = mStrokeWidthInt
    }

}