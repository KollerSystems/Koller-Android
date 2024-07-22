package com.norbert.koller.teacher.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.slider.RangeSlider
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.orderSingleNumber
import com.norbert.koller.teacher.R

class TimeRangeView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    val rangeSlider: RangeSlider

    init {

        orientation = VERTICAL

        this.inflate()

        rangeSlider = findViewById(R.id.RangeSlider_time)

        rangeSlider.values = listOf(1f,1f)

        val textTimeRange : TextView = findViewById(R.id.Text_time_range)

        fun setSliderText(distance : Float){
            textTimeRange.text = "Időtartam: " + ApplicationManager.createClassesText(context, rangeSlider.values[0].toInt() - 1, distance.toInt() + 1)
        }

        fun sliderDistance() : Float{
            return rangeSlider.values[1] - rangeSlider.values[0]
        }

        setSliderText(sliderDistance())

        rangeSlider.addOnChangeListener { slider, value, fromUser ->

            val distance = sliderDistance()
            if (distance >= 4f) {
                if(fromUser) {
                    if (value > rangeSlider.values[0]) {
                        rangeSlider.setValues(value - 1, rangeSlider.values[1])
                    } else {
                        rangeSlider.setValues(rangeSlider.values[0], value + 1)
                    }
                }
            }
            else{
                setSliderText(distance)
            }

        }

        rangeSlider.setLabelFormatter { value ->
            "${value.toInt().orderSingleNumber(context)} ${context.getString(com.norbert.koller.shared.R.string.lesson).lowercase()}"
        }

    }

    fun inflate(){
        View.inflate(context, R.layout.view_time_range, this)
    }

}