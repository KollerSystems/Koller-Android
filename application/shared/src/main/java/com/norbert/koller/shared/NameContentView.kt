package com.norbert.koller.shared

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView



open class NameContentView(context: Context, attrs: AttributeSet) : NameContentShared(context, attrs) {


    lateinit var textContent: TextView


    override fun inflate(){
        View.inflate(context, R.layout.name_content_view, this)

        textContent = findViewById(R.id.text_content)
        textContent.text = mContent
    }

}