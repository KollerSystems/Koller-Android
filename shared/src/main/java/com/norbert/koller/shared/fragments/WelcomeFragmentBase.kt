package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider

abstract class WelcomeFragmentBase : Fragment() {


    fun waitForChange(vararg checkboxes: MaterialCheckBox) {

        for (checkbox in checkboxes) {
            checkbox.setOnCheckedChangeListener { _, _ ->
                onChange?.invoke(checkIfAllCorrect())
            }
        }
    }

    fun waitForChange(vararg sliders: RangeSlider) {

        for (slider in sliders) {
            slider.addOnChangeListener{ _, _, _ ->
                onChange?.invoke(checkIfAllCorrect())
            }
        }
    }

    fun waitForChange(vararg editTexts: EditText) {

        for (editText in editTexts) {
            editText.doOnTextChanged { _, _, _, _ ->
                onChange?.invoke(checkIfAllCorrect())
            }
        }
    }

    var onChange: ((Boolean) -> Unit)? = null

    abstract fun checkIfAllCorrect() : Boolean

}