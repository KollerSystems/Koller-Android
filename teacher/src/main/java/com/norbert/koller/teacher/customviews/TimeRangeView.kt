package com.norbert.koller.teacher.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.slider.RangeSlider
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.orderSingleNumber
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.databinding.ViewTimeRangeBinding

class TimeRangeView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    val biding = ViewTimeRangeBinding.inflate(LayoutInflater.from(context), this)

    fun setSliderText(distance : Float){
        biding.text.text = "Időtartam: " + ApplicationManager.createClassesText(context, biding.rangeSlider.values[0].toInt() - 1, distance.toInt() + 1)
    }

    fun sliderDistance() : Float{
        return biding.rangeSlider.values[1] - biding.rangeSlider.values[0]
    }

    init {

        orientation = VERTICAL

        biding.rangeSlider.values = listOf(1f,1f)

        setSliderText(sliderDistance())
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        biding.rangeSlider.addOnChangeListener { slider, value, fromUser ->

            val distance = sliderDistance()
            if (distance >= 4f) {
                if(fromUser) {
                    if (value > biding.rangeSlider.values[0]) {
                        biding.rangeSlider.setValues(value - 1, biding.rangeSlider.values[1])
                    } else {
                        biding.rangeSlider.setValues(biding.rangeSlider.values[0], value + 1)
                    }
                }
            }
            else{
                setSliderText(distance)
            }

        }

        biding.rangeSlider.setLabelFormatter { value ->
            "${value.toInt().orderSingleNumber(context)} ${context.getString(com.norbert.koller.shared.R.string.lesson).lowercase()}"
        }
    }

}