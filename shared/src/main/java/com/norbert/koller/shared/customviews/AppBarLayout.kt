package com.norbert.koller.shared.customviews

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.Button
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.managers.setupLandscape
import com.norbert.koller.shared.managers.setupPortrait


class AppBarLayout : com.google.android.material.appbar.AppBarLayout {

    lateinit var backButton : Button

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    init {

    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        setBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainer))
        val landscape = (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)

            backButton = if (landscape) {
                setupLandscape()

            } else {
                setupPortrait()
            }


    }

}