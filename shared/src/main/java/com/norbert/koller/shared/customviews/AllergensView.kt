package com.norbert.koller.shared.customviews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ButtonCardBinding
import com.norbert.koller.shared.databinding.ViewAllergensBinding
import com.norbert.koller.shared.databinding.ViewSmallBinding
import com.norbert.koller.shared.managers.setVisibilityBy

class AllergensView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    companion object{
        const val CONTAINS = 0
        const val MAY_CONTAIN = 1
    }

    private var mType: Int = CONTAINS
    private var mDesc: String = ""
    private var mIcon: Drawable? = null

    private var binding : ViewAllergensBinding

    fun getImageIcon() : ImageView{
        return binding.image
    }
    fun getTextDescription() : TextView{
        return binding.text
    }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.allergensView,
            0, 0
        )

        try {
            mType = typedArray.getInt(R.styleable.allergensView_allergen_type, 0)
            mDesc = typedArray.getString(R.styleable.allergensView_description) ?: ""
            mIcon = typedArray.getDrawable(R.styleable.allergensView_icon)
        } finally {
            typedArray.recycle()
        }

        binding = ViewAllergensBinding.inflate(LayoutInflater.from(context), this)
        orientation = VERTICAL

    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        getImageIcon().setImageDrawable(mIcon)
        val stringId = when (mType){
            MAY_CONTAIN ->{
                getImageIcon().foreground = AppCompatResources.getDrawable(context, R.drawable.ring_maybe)
                R.string.may_contain
            }
            else -> {
                getImageIcon().foreground = AppCompatResources.getDrawable(context, R.drawable.ring_warning)
                R.string.contains
            }
        }
        getTextDescription().text = context.getString(stringId, mDesc)
        requestLayout()
    }
}