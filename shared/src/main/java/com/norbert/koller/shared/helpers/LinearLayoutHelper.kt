package com.norbert.koller.shared.helpers

import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.norbert.koller.shared.R

class LinearLayoutHelper {
}

fun ViewGroup.setMaxWidth(){
    viewTreeObserver.addOnGlobalLayoutListener {
        val width = width


        if (width != tag) {
            val tabletMaxWidth = resources.getDimensionPixelSize(R.dimen.tablet_max_width)
            val fullCardPadding = resources.getDimensionPixelSize(R.dimen.card_padding)
            if (this.measuredWidth - fullCardPadding * 2 > tabletMaxWidth) {
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
                    fullCardPadding,
                    this.paddingTop,
                    fullCardPadding,
                    this.paddingBottom
                )
            }
            tag = width
        }
    }
}