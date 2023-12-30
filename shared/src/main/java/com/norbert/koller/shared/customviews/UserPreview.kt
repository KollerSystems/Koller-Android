package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.norbert.koller.shared.R

class UserPreview (context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    //TODO: Recycler view átvariálása ide

    val userBadge : RoundedBadgeImageView
    val text : TextView

    init {
        View.inflate(context, R.layout.view_tip, this)
        userBadge = findViewById(R.id.button_close)
        text = findViewById(R.id.text_name)
    }

}