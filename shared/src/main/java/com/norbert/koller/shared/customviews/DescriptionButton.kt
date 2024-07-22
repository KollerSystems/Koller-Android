package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.norbert.koller.shared.R

class DescriptionButton(context: Context, attrs: AttributeSet) : Description(context, attrs) {

    lateinit var buttonContent: Button

    override fun inflate(){
        View.inflate(context, R.layout.button_description, this)

        buttonContent = findViewById(R.id.button_content)
        buttonContent.text = mContent
    }

}