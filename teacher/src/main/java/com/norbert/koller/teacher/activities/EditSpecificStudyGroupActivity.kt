package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.customviews.TimeRangeCardView

open class EditSpecificStudyGroupActivity : AppCompatActivity() {

    lateinit var cardDays : MaterialCardView

    lateinit var tilTitle : TextInputLayout
    lateinit var tilDescription : TextInputLayout
    lateinit var tilDate : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_study_group)



        val appBar : AppBarLayout = findViewById(R.id.appbar)

        appBar.setup()

        val backButton : Button = findViewById(R.id.toolbar_exit)

        backButton.setOnClickListener{
            onBackPressed()
        }

        val saveButton : Button = findViewById(R.id.button_publish_all)

        saveButton.setOnClickListener {

        }

        cardDays = findViewById(R.id.Mc_days)

        tilTitle = findViewById(R.id.Til_title)
        tilDescription = findViewById(R.id.Til_description)
        tilDate = findViewById(R.id.Til_date)

        val checkBoxMonday : CheckBox = findViewById(R.id.CheckBox_monday)
        val checkBoxTuesday : CheckBox = findViewById(R.id.CheckBox_thuesday)
        val checkBoxWednesday : CheckBox = findViewById(R.id.CheckBox_wednessday)
        val checkBoxThursday : CheckBox = findViewById(R.id.CheckBox_thursday)

        val timeRangeMonday : TimeRangeCardView = findViewById(R.id.Trcw_monday)
        val timeRangeTuesday : TimeRangeCardView = findViewById(R.id.Trcw_thuesday)
        val timeRangeWednesday : TimeRangeCardView = findViewById(R.id.Trcv_wednessday)
        val timeRangeThursday : TimeRangeCardView = findViewById(R.id.Trcv_thursday)

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
    }
}