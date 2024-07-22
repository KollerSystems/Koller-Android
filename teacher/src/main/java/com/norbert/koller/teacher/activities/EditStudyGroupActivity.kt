package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.teacher.R


class EditStudyGroupActivity : EditSpecificStudyGroupActivity() {


    lateinit var radioText : TextView

    lateinit var radioGroupToEdit : RadioGroup

    lateinit var cardTimeRange : MaterialCardView

    lateinit var cardRepeat : MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardRepeat = findViewById(R.id.Mc_repeat)

        radioText = findViewById(R.id.Text_radio)

        radioGroupToEdit = findViewById(R.id.RadioGroup_to_edit)

        cardTimeRange = findViewById(R.id.Mc_range)

        radioText.isVisible = true
        radioGroupToEdit.isVisible = true



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

        radioGroupToEdit.check(R.id.RadioButton_only_this)
    }

}