package com.norbert.koller.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.card.MaterialCardView

class RoundedBadgeImageView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var mStrokeWidth: Float = 0f
    lateinit var image : ImageView
    lateinit var card : MaterialCardView

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