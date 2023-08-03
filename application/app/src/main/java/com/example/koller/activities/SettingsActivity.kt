package com.example.koller.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import com.example.koller.R
import com.example.shared.SimpleCardButton
import com.example.shared.SimpleCardButtonWithToggle
import com.example.shared.activities.TestActivity
import com.example.shared.api.APIInterface
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class SettingsActivity : com.example.shared.activities.SettingsActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_settings)
        super.onCreate(savedInstanceState)
        val checkBoxParent: SimpleCardButtonWithToggle = findViewById(R.id.notifics_all)
        val childrenCheckBoxes: ArrayList<SimpleCardButtonWithToggle> = arrayListOf(
            findViewById(R.id.notifics_arrival),
            findViewById(R.id.notifics_new),
            findViewById(R.id.notifics_room),
            findViewById(R.id.notifics_occupation),
            findViewById(R.id.notifics_comm_or_warn))

        // Parent's checked state changed listener
        val parentOnCheckedStateChangedListener =
            MaterialCheckBox.OnCheckedStateChangedListener { checkBox: MaterialCheckBox, state: Int ->
                val isChecked = checkBox.isChecked
                if (state != MaterialCheckBox.STATE_INDETERMINATE) {
                    isUpdatingChildren = true
                    for (child in childrenCheckBoxes) {
                        child.checkBox.isChecked = isChecked
                    }
                    isUpdatingChildren = false
                }
            }

        checkBoxParent.checkBox.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)


        val childOnCheckedStateChangedListener =
            MaterialCheckBox.OnCheckedStateChangedListener { checkBox: MaterialCheckBox?, state: Int ->
                if (!isUpdatingChildren) {
                    setParentState(
                        checkBoxParent,
                        childrenCheckBoxes,
                        parentOnCheckedStateChangedListener
                    )
                }
            }
        for (child in childrenCheckBoxes) {
            (child.checkBox)
                .addOnCheckedStateChangedListener(childOnCheckedStateChangedListener)
        }
    }

}