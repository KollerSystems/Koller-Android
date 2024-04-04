package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.orderSingleNumber
import com.norbert.koller.shared.managers.setToolbarToViewColor
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R
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

    }
}