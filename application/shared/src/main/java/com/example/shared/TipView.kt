package com.example.shared

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView


class TipView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var mText: String = ""

    private val textText: TextView
    val buttonClose: Button

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.tipView,
            0, 0
        )

        try {
            mText = typedArray.getString(R.styleable.tipView_text) ?: ""
        } finally {
            typedArray.recycle()
        }

        View.inflate(context, R.layout.view_tip, this)
        textText = findViewById(R.id.text_view)
        buttonClose = findViewById(R.id.button_close)

        textText.text = mText

        buttonClose.setOnClickListener{
            visibility = GONE
        }
    }

}