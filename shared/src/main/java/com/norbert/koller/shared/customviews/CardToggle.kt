package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.checkbox.MaterialCheckBox
import com.norbert.koller.shared.R

class CardToggle(context: Context, attrs: AttributeSet) : CardButton(context, attrs) {

    override fun inflate() {
        View.inflate(context, R.layout.item_toggle, this)
    }

    val checkBox: MaterialCheckBox = findViewById(R.id.toggle)

    init {

        setOnClickListener{
            checkBox.toggle()
        }
    }

}