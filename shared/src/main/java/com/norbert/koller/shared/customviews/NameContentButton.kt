package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.norbert.koller.shared.R

class NameContentButton(context: Context, attrs: AttributeSet) : NameContentShared(context, attrs) {

    lateinit var buttonContent: Button

    override fun inflate(){
        View.inflate(context, R.layout.name_content_button, this)

        buttonContent = findViewById(R.id.button_content)
        buttonContent.text = mContent
    }

}