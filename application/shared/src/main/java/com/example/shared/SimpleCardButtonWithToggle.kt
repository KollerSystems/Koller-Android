package com.example.shared

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.google.android.material.checkbox.MaterialCheckBox

class SimpleCardButtonWithToggle(context: Context, attrs: AttributeSet) : SimpleCardButton(context, attrs) {

    override fun inflate() {
        View.inflate(context, R.layout.simple_card_button_with_toggle, this)
    }

    val checkBox: MaterialCheckBox

    init {
        checkBox = findViewById(R.id.toggle)

        setOnClickListener{
            checkBox.toggle()
        }
    }

}