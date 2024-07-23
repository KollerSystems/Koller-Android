package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.checkbox.MaterialCheckBox
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ToggleCardBinding

class CardToggle(context: Context, attrs: AttributeSet) : Card(context, attrs) {

    lateinit var binding : ToggleCardBinding

    override fun getImageIcon(): ImageView {
        return binding.imageIcon
    }

    override fun getTextTitle(): TextView {
        return binding.textTitle
    }

    override fun getTextDescription(): TextView {
        return binding.textDescription
    }

    override fun inflate() {
        binding = ToggleCardBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun getCheckBox() : MaterialCheckBox{
        return binding.cb
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        setOnClickListener{
            binding.cb.toggle()
        }
    }

}