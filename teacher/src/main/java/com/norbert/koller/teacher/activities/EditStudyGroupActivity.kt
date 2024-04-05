package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.orderSingleNumber
import com.norbert.koller.shared.managers.setToolbarToViewColor
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.customviews.TimeRangeCardView
import java.util.Locale


class EditStudyGroupActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_study_group)


        window.setToolbarToViewColor(findViewById(R.id.bottom_view))

        val appBar : AppBarLayout = findViewById(R.id.appbar)

        appBar.setup()

        val backButton : Button = findViewById(R.id.toolbar_exit)

        backButton.setOnClickListener{
            onBackPressed()
        }

        val saveButton : Button = findViewById(R.id.button_publish_all)

        saveButton.setOnClickListener {

        }

        val radioGroupToEdit : RadioGroup = findViewById(R.id.RadioGroup_to_edit)

        val cardDays : MaterialCardView = findViewById(R.id.Mc_days)

        val tilTitle : TextInputLayout = findViewById(R.id.Til_title)
        val tilDescription : TextInputLayout = findViewById(R.id.Til_description)
        val tilDate : TextInputLayout = findViewById(R.id.Til_date)

        val checkBoxMonday : CheckBox = findViewById(R.id.CheckBox_monday)
        val checkBoxTuesday : CheckBox = findViewById(R.id.CheckBox_thuesday)
        val checkBoxWednesday : CheckBox = findViewById(R.id.CheckBox_wednessday)
        val checkBoxThursday : CheckBox = findViewById(R.id.CheckBox_thursday)

        val timeRangeMonday : TimeRangeCardView = findViewById(R.id.Trcw_monday)
        val timeRangeTuesday : TimeRangeCardView = findViewById(R.id.Trcw_thuesday)
        val timeRangeWednesday : TimeRangeCardView = findViewById(R.id.Trcv_wednessday)
        val timeRangeThursday : TimeRangeCardView = findViewById(R.id.Trcv_thursday)

        val cardRepeat : MaterialCardView = findViewById(R.id.Mc_repeat)
        val cardTimeRange : MaterialCardView = findViewById(R.id.Mc_range)

        checkBoxMonday.setOnCheckedChangeListener{_, isChecked ->
            timeRangeMonday.isVisible = isChecked
        }

        checkBoxTuesday.setOnCheckedChangeListener{_, isChecked ->
            timeRangeTuesday.isVisible = isChecked
        }

        checkBoxWednesday.setOnCheckedChangeListener{_, isChecked ->
            timeRangeWednesday.isVisible = isChecked
        }

        checkBoxThursday.setOnCheckedChangeListener{_, isChecked ->
            timeRangeThursday.isVisible = isChecked
        }

        radioGroupToEdit.setOnCheckedChangeListener { radioGroup, id ->
            val onlyThis = id == R.id.RadioButton_only_this

            cardDays.isVisible = !onlyThis
            cardRepeat.isVisible = !onlyThis
            cardTimeRange.isVisible = onlyThis
            tilTitle.isEnabled = !onlyThis
            tilDescription.isEnabled = !onlyThis

            if(onlyThis){
                tilDate.hint = getString(com.norbert.koller.shared.R.string.date)
            }
            else{
                tilDate.hint = "Kezdés dátuma"
            }
        }
    }
}