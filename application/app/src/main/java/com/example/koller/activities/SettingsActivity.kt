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

    private var isUpdatingChildren = false

    private fun setParentState(
        checkBoxParent: SimpleCardButtonWithToggle,
        childrenCheckBoxes: List<SimpleCardButtonWithToggle>,
        parentOnCheckedStateChangedListener: MaterialCheckBox.OnCheckedStateChangedListener
    ) {
        val checkedCount = childrenCheckBoxes.stream().filter { obj: SimpleCardButtonWithToggle -> obj.checkBox.isChecked }
            .count()
            .toInt()
        val allChecked = checkedCount == childrenCheckBoxes.size
        val noneChecked = checkedCount == 0
        checkBoxParent.checkBox.removeOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
        if (allChecked) {
            checkBoxParent.checkBox.isChecked = true
        } else if (noneChecked) {
            checkBoxParent.checkBox.isChecked = false
        } else {
            checkBoxParent.checkBox.checkedState = MaterialCheckBox.STATE_INDETERMINATE
        }
        checkBoxParent.checkBox.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val settingsSCB : SimpleCardButton = findViewById(R.id.settings_scb_test_activity)

        settingsSCB.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }


        val rangeSlider : RangeSlider = findViewById(R.id.settings_slider_list_loading_delay)

        rangeSlider.setValues(APIInterface.loadingDelayFrom, APIInterface.loadingDelayTo)

        rangeSlider.addOnChangeListener{ slider, value, fromUser ->

            APIInterface.loadingDelayFrom = slider.values[0]
            APIInterface.loadingDelayTo = slider.values[1]

        }



            findViewById<Button>(R.id.toolbar_exit).setOnClickListener{
                onBackPressed()
            }

            val timeOffsetSlider : Slider = findViewById(R.id.settings_slider_time_offset)
            var c : Calendar = Calendar.getInstance()
            val hours : Float = (c.get(Calendar.SECOND)  / 60f / 60f+ c.get(Calendar.MINUTE) / 60f + c.get(Calendar.HOUR_OF_DAY))
            var hoursTIL : TextInputLayout = findViewById(R.id.settings_til_hours)
            var hoursTIET : TextInputEditText = findViewById(R.id.settings_tiet_hours)
            hoursTIET.setText((timeOffset).toString())


            timeOffsetSlider.value = timeOffset

            hoursTIL.hint = (hours + timeOffset).toString()

            timeOffsetSlider.addOnChangeListener { slider, value, fromUser ->
                timeOffset = value
                if(timeOffsetSlider.value.toString() != hoursTIET.text.toString())
                    hoursTIET.setText((timeOffset).toString())
            }

            hoursTIET.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {

                    try {
                        if(hoursTIET.text.toString() == "") hoursTIET.setText("0")
                        if(hoursTIET.text.toString().toFloat() > 24) hoursTIET.setText((24).toString())
                        if(hoursTIET.text.toString().toFloat() < -24) hoursTIET.setText((-24).toString())
                        if(timeOffsetSlider.value.toString() != hoursTIET.text.toString())
                            timeOffsetSlider.value = hoursTIET.text.toString().toFloat()
                        hoursTIL.hint = (hours + timeOffset).toString()
                    }catch(asd : Exception){

                    }

                }
            })


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

            // Checked state changed listener for each child
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