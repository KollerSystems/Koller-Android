package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.norbert.koller.shared.R

abstract class Description(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    abstract fun getTextTitle() : TextView
    abstract fun getTextDescription() : TextView

    private var mTitle: String = ""
    var mDescription: String = ""
    private var mIconID: Int = 0


    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.nameContentView,
            0, 0
        )

        try {
            mTitle = typedArray.getString(R.styleable.nameContentView_name) ?: ""
            mDescription = typedArray.getString(R.styleable.nameContentView_detail) ?: ""
            mIconID = typedArray.getResourceId(R.styleable.nameContentView_icon, 0)
        } finally {
            typedArray.recycle()
        }

        this.inflate()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        getTextTitle().text = mTitle
        getTextTitle().setCompoundDrawablesWithIntrinsicBounds(mIconID, 0, 0, 0);

        getTextDescription().text = mDescription
    }

    abstract fun inflate()
}