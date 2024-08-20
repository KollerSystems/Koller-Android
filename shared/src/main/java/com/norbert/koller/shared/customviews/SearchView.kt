package com.norbert.koller.shared.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ViewSearchBinding
import com.norbert.koller.shared.managers.setVisibilityBy

@SuppressLint("SetTextI18n")
class SearchView : MaterialCardView {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    private val binding = ViewSearchBinding.inflate(LayoutInflater.from(context), this, true)

    fun getEditText() : EditText{
        return binding.et
    }

    fun getButton() : Button{
        return binding.btn
    }

    init {

        this.radius = 9999f

        binding.et.doOnTextChanged{_,_,_,_->
            binding.btn.setVisibilityBy(!binding.et.text.isNullOrEmpty())
        }

        binding.btn.setOnClickListener{
            binding.et.setText("")
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()


    }

}