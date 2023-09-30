package com.norbert.koller.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class RoundedBadgeImageView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.view_rounded_badge_image, this)
    }

}