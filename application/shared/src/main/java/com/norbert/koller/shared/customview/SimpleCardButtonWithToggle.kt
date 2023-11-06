package com.norbert.koller.shared.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.checkbox.MaterialCheckBox
import com.norbert.koller.shared.R

class SimpleCardButtonWithToggle(context: Context, attrs: AttributeSet) : SimpleCardButton(context, attrs) {

    override fun inflate() {
        View.inflate(context, R.layout.simple_card_button_with_toggle, this)
    }

    val checkBox: MaterialCheckBox = findViewById(R.id.toggle)

    init {

        setOnClickListener{
            checkBox.toggle()
        }
    }

}