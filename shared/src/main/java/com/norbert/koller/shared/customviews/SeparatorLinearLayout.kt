package com.norbert.koller.shared.customviews

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.core.view.isVisible
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor

class SeparatorLinearLayout : LinearLayout {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    init {



    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        var count : Int = 0
        for (child in children){

            if(child.isVisible){
                if(count % 2 == 0) {
                    child.setBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerLow))
                }
                count++
            }
        }
    }



}