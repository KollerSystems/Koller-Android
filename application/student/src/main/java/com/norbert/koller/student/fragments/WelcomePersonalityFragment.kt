package com.norbert.koller.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.slider.RangeSlider
import com.norbert.koller.shared.fragments.WelcomeFragmentBase
import com.norbert.koller.student.R
import com.norbert.koller.student.activities.WelcomeActivity

class WelcomePersonalityFragment : WelcomeFragmentBase() {

    lateinit var rangeSliderPersonality : RangeSlider

    override fun checkIfAllCorrect(): Boolean {
        return (sliderHasBeenMoved)
    }

    var sliderHasBeenMoved : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_personality, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rangeSliderPersonality = view.findViewById(R.id.range_slider_personality)
        rangeSliderPersonality.setValues(5f, 5f)
        rangeSliderPersonality.addOnChangeListener { _, _, _ ->

            sliderHasBeenMoved = true

        }

        waitForChange(rangeSliderPersonality)
    }

}