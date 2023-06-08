package com.example.koller.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.example.koller.MyApplication
import com.example.koller.R
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        var timeOffset : Float = 0f
    }

    private var isUpdatingChildren = false

    private fun setParentState(
        checkBoxParent: MaterialCheckBox,
        childrenCheckBoxes: List<CheckBox>,
        parentOnCheckedStateChangedListener: MaterialCheckBox.OnCheckedStateChangedListener
    ) {
        val checkedCount = childrenCheckBoxes.stream().filter { obj: CheckBox -> obj.isChecked }
            .count()
            .toInt()
        val allChecked = checkedCount == childrenCheckBoxes.size
        val noneChecked = checkedCount == 0
        checkBoxParent.removeOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
        if (allChecked) {
            checkBoxParent.isChecked = true
        } else if (noneChecked) {
            checkBoxParent.isChecked = false
        } else {
            checkBoxParent.checkedState = MaterialCheckBox.STATE_INDETERMINATE
        }
        checkBoxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


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


        val checkBoxParent: MaterialCheckBox = findViewById(R.id.notifics_all)
        val childrenCheckBoxes: ArrayList<MaterialCheckBox> = arrayListOf(findViewById(R.id.notifics_arrival), findViewById(
            R.id.notifics_new
        ), findViewById(R.id.notifics_room), findViewById(R.id.notifics_occupation), findViewById(R.id.notifics_comm_or_warn))

        // Parent's checked state changed listener
        val parentOnCheckedStateChangedListener =
            MaterialCheckBox.OnCheckedStateChangedListener { checkBox: MaterialCheckBox, state: Int ->
                val isChecked = checkBox.isChecked
                if (state != MaterialCheckBox.STATE_INDETERMINATE) {
                    isUpdatingChildren = true
                    for (child in childrenCheckBoxes) {
                        child.isChecked = isChecked
                    }
                    isUpdatingChildren = false
                }
            }

        checkBoxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)

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
            (child as MaterialCheckBox)
                .addOnCheckedStateChangedListener(childOnCheckedStateChangedListener)
        }
    }
}