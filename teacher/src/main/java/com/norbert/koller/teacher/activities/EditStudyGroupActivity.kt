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

            binding.mcardDays.isVisible = !onlyThis
            binding.mcardRepeat.isVisible = !onlyThis
            binding.mcardRange.isVisible = onlyThis
            binding.tilTitle.isEnabled = !onlyThis
            binding.tilDescription.isEnabled = !onlyThis

            if(onlyThis){
                binding.tilDate.hint = getString(com.norbert.koller.shared.R.string.date)
            }
            else{
                binding. tilDate.hint = "Kezdés dátuma"
            }
        }

        binding.rgroupToEdit.check(R.id.rbtn_only_this)
    }

}