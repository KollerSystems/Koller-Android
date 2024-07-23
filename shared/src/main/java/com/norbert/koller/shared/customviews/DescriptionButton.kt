package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ButtonCardBinding
import com.norbert.koller.shared.databinding.ButtonDescriptionBinding

class DescriptionButton(context: Context, attrs: AttributeSet) : Description(context, attrs) {

    lateinit var binding: ButtonDescriptionBinding
    override fun getTextTitle(): TextView {
        return binding.textTitle
    }

    override fun getTextDescription(): TextView {
        return binding.buttonDescription
    }

    override fun inflate(){
        binding = ButtonDescriptionBinding.inflate(LayoutInflater.from(context), this, true)
    }

}