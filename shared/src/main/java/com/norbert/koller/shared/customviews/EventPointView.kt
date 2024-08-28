package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ViewEventPointBinding

class EventPointView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var mLabel: String = ""
    private var mTime: String = ""


    lateinit var binding : ViewEventPointBinding

    init {

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.eventPoint,
            0, 0
        )

        try {
            mLabel = typedArray.getString(R.styleable.eventPoint_text) ?: ""
            mTime = typedArray.getString(R.styleable.eventPoint_time) ?: ""
        } finally {
            typedArray.recycle()
        }

        binding = ViewEventPointBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        binding.textLabel.text = mLabel

        binding.textTime.text = mTime
    }

}