package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.norbert.koller.shared.R


open class DescriptionView(context: Context, attrs: AttributeSet) : Description(context, attrs) {


    lateinit var textContent: TextView


    override fun inflate(){
        View.inflate(context, R.layout.view_description, this)

        textContent = findViewById(R.id.text_content)
        textContent.text = mContent
    }

}