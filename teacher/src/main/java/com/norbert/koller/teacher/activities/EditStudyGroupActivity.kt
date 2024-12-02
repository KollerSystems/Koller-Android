package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.teacher.R


class EditStudyGroupActivity : EditSpecificStudyGroupActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.textRadio.isVisible = true
        binding.rgroupToEdit.isVisible = true



        binding.rgroupToEdit.setOnCheckedChangeListener { radioGroup, id ->
            val onlyThis = id == R.id.rbtn_only_this

            binding.checkBoxMonday.isVisible = !onlyThis
            binding.checkBoxTuesday.isVisible = !onlyThis
            binding.checkBoxWednesday.isVisible = !onlyThis
            binding.checkBoxThursday.isVisible = !onlyThis
            binding.trcwMonday.isVisible = !onlyThis
            binding.trcvTuesday.isVisible = !onlyThis
            binding.trcvWednesday.isVisible = !onlyThis
            binding.trcvThursday.isVisible = !onlyThis
            binding.mcardRange.isVisible = onlyThis
            binding.tilTitle.isEnabled = !onlyThis
            binding.tilDescription.isEnabled = !onlyThis

            if(onlyThis){
                binding.tilDate.hint = getString(com.norbert.koller.shared.R.string.date)
            }
            else{
                binding. tilDate.hint = "Kezdés dátuma"
                binding.checkBoxMonday.isChecked = false
                binding.checkBoxTuesday.isChecked = false
                binding.checkBoxWednesday.isChecked = false
                binding.checkBoxThursday.isChecked = false
            }
        }

        binding.rgroupToEdit.check(R.id.rbtn_only_this)
    }

}