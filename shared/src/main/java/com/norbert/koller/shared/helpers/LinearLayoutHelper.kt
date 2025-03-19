package com.norbert.koller.shared.helpers

import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.norbert.koller.shared.R

class LinearLayoutHelper {
}

fun ViewGroup.setMaxWidth(defaultPadding : Int = resources.getDimensionPixelSize(R.dimen.application_padding)){



    viewTreeObserver.addOnGlobalLayoutListener {
        val width = width


        if (width != tag) {
            val tabletMaxWidth = resources.getDimensionPixelSize(R.dimen.tablet_max_width)
            if (this.measuredWidth - defaultPadding * 2 > tabletMaxWidth) {
                Log.d("TAGHELLO", "AJAJAJAJAJ")
                val correctPadding = (this.measuredWidth - tabletMaxWidth) / 2
                this.setPadding(
                    correctPadding,
                    this.paddingTop,
                    correctPadding,
                    this.paddingBottom
                )
            }
            else{
                this.setPadding(
                    defaultPadding,
                    this.paddingTop,
                    defaultPadding,
                    this.paddingBottom
                )
            }
            tag = width
        }
    }
}