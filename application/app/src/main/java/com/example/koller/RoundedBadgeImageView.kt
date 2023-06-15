package com.example.koller

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView

class RoundedBadgeImageView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.rounded_badge_image_view, this)
    }

}