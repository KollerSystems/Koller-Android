package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.norbert.koller.shared.R

abstract class NameContentShared(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var mName: String = ""
    var mContent: String = ""
    private var mIconID: Int = 0

    private val textName: TextView


    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.nameContentView,
            0, 0
        )

        try {
            mName = typedArray.getString(R.styleable.nameContentView_name) ?: ""
            mContent = typedArray.getString(R.styleable.nameContentView_detail) ?: ""
            mIconID = typedArray.getResourceId(R.styleable.nameContentView_icon, 0)
        } finally {
            typedArray.recycle()
        }

        this.inflate()

        textName = findViewById(R.id.text_name)


        textName.text = mName
        textName.setCompoundDrawablesWithIntrinsicBounds(mIconID, 0, 0, 0);


    }

    abstract fun inflate()

}