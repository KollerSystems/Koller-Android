package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.norbert.koller.shared.R

class EventPoint(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var mLabel: String = ""
    private var mTime: String = ""

    private val textLabel: TextView
    private val textTime: TextView

    init {

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.eventPoint,
            0, 0
        )

        try {
            mLabel = typedArray.getString(R.styleable.eventPoint_text) ?: ""
            mTime = typedArray.getString(R.styleable.eventPoint_time) ?: ""
        } finally {
            typedArray.recycle()
        }

        View.inflate(context, R.layout.event_point, this)


        textLabel = findViewById(R.id.text_label)

        textLabel.text = mLabel

        textTime = findViewById(R.id.text_time)

        textTime.text = mTime

    }

}