package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ButtonDescriptionBinding
import com.norbert.koller.shared.databinding.ViewDescriptionBinding


open class DescriptionView(context: Context, attrs: AttributeSet) : Description(context, attrs) {

    lateinit var binding: ViewDescriptionBinding
    override fun getTextTitle(): TextView {
        return binding.textTitle
    }

    override fun getTextDescription(): TextView {
        return binding.textDescription
    }

    override fun inflate(){
        binding = ViewDescriptionBinding.inflate(LayoutInflater.from(context), this, true)
    }

}