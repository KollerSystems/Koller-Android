package com.norbert.koller.student.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.EditText
import android.widget.ScrollView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.slider.RangeSlider
import com.norbert.koller.shared.databinding.ContentDataSchoolBinding
import com.norbert.koller.student.activities.RegisterActivity

abstract class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewGroup : ViewGroup = createBinding()
        val scrollView = ScrollView(context)
        scrollView.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        scrollView.isFillViewport = true
        scrollView.addView(viewGroup)
        return scrollView
    }

    fun getRegisterActivity() : RegisterActivity{
        return context as RegisterActivity
    }

    abstract fun createBinding() : ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val registerActivity = context as com.norbert.koller.student.activities.RegisterActivity

        val linearLayout = (view as ViewGroup).getChildAt(0)

        linearLayout.post {
            linearLayout.setPadding(linearLayout.paddingLeft, registerActivity.topPadding, linearLayout.paddingRight, registerActivity.bottomPadding)
        }

    }

    override fun onResume() {
        super.onResume()

        onChange(checkIfAllCorrect())
        (context as com.norbert.koller.student.activities.RegisterActivity).onFragmentResume()
    }

    fun waitForChange(vararg checkboxes: MaterialCheckBox) {

        for (checkbox in checkboxes) {
            checkbox.setOnCheckedChangeListener { _, _ ->
                onChange(checkIfAllCorrect())
            }
        }
    }

    fun waitForChange(vararg sliders: RangeSlider) {

        for (slider in sliders) {
            slider.addOnChangeListener{ _, _, _ ->
                onChange(checkIfAllCorrect())
            }
        }
    }

    fun waitForChange(vararg editTexts: EditText) {

        for (editText in editTexts) {
            editText.doOnTextChanged { _, _, _, _ ->
                onChange(checkIfAllCorrect())
            }
        }
    }

    fun onChange(allCorrect: Boolean){
        (context as com.norbert.koller.student.activities.RegisterActivity).binding.buttonNext.isEnabled = allCorrect
    }

    abstract fun checkIfAllCorrect() : Boolean

}