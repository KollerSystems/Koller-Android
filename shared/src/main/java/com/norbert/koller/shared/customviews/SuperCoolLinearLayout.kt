package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.helpers.setMaxWidth

class SuperCoolLinearLayout : LinearLayout {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    init {
        this.setMaxWidth(paddingStart)
    }
}