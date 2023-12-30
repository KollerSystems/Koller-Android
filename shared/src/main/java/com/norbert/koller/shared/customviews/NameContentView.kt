package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.norbert.koller.shared.R


open class NameContentView(context: Context, attrs: AttributeSet) : NameContentShared(context, attrs) {


    lateinit var textContent: TextView


    override fun inflate(){
        View.inflate(context, R.layout.name_content_view, this)

        textContent = findViewById(R.id.text_content)
        textContent.text = mContent
    }

}