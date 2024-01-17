package com.norbert.koller.shared.activities

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.customviews.SimpleCardButtonWithToggle
import com.norbert.koller.shared.api.APIInterface
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.managers.setup
import java.util.Calendar
import java.util.Locale

open class SettingsActivity : AppCompatActivity() {
    companion object {
        var timeOffset : Float = 0f
    }

    var isUpdatingChildren = false

    fun setParentState(
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

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val settingsSCB : SimpleCardButton = findViewById(R.id.settings_scb_test_activity)




        val appBar : AppBarLayout = findViewById(R.id.appbar)

        appBar.setup()

        settingsSCB.setOnClickListener {
            val intent = Intent(this, CreateNewPostActivity::class.java)
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
        val c : Calendar = Calendar.getInstance()
        val hours : Float = (c.get(Calendar.SECOND)  / 60f / 60f+ c.get(Calendar.MINUTE) / 60f + c.get(Calendar.HOUR_OF_DAY))
        val hoursTIL : TextInputLayout = findViewById(R.id.settings_til_hours)
        hoursTIL.editText!!.setText((timeOffset).toString())


        timeOffsetSlider.value = timeOffset

        hoursTIL.hint = (hours + timeOffset).toString()

        timeOffsetSlider.addOnChangeListener { slider, value, fromUser ->
            timeOffset = value
            if(timeOffsetSlider.value.toString() != hoursTIL.editText!!.text.toString())
                hoursTIL.editText!!.setText((timeOffset).toString())
        }

        hoursTIL.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                try {
                    if(hoursTIL.editText!!.text.toString() == "") hoursTIL.editText!!.setText("0")
                    if(hoursTIL.editText!!.text.toString().toFloat() > 24) hoursTIL.editText!!.setText((24).toString())
                    if(hoursTIL.editText!!.text.toString().toFloat() < -24) hoursTIL.editText!!.setText((-24).toString())
                    if(timeOffsetSlider.value.toString() != hoursTIL.editText!!.text.toString())
                        timeOffsetSlider.value = hoursTIL.editText!!.text.toString().toFloat()
                    hoursTIL.hint = (hours + timeOffset).toString()
                }catch(asd : Exception){

                }

            }
        })

        val notifFullSettings : SimpleCardButton = findViewById(R.id.notifics_full_settings)

        notifFullSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            startActivity(intent)
        }


    }
}