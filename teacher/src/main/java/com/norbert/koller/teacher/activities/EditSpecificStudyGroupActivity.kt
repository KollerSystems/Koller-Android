package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.activities.ManageActivity
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.customviews.TimeRangeView
import com.norbert.koller.teacher.databinding.ActivityEditStudyGroupBinding

open class EditSpecificStudyGroupActivity : ManageActivity() {

    lateinit var binding : ActivityEditStudyGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_study_group)

        binding.checkBoxMonday.setOnCheckedChangeListener{_, isChecked ->
            binding.trcwMonday.isVisible = isChecked
        }

        binding.checkBoxTuesday.setOnCheckedChangeListener{_, isChecked ->
            binding.trcvTuesday.isVisible = isChecked
        }

        binding.checkBoxWednesday.setOnCheckedChangeListener{_, isChecked ->
            binding.trcvWednesday.isVisible = isChecked
        }

        binding.checkBoxThursday.setOnCheckedChangeListener{_, isChecked ->
            binding.trcvThursday.isVisible = isChecked
        }
    }

    override fun createContentView(): ViewGroup {
        binding = ActivityEditStudyGroupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getName(): String {
        return getString(R.string.edit_study_group)
    }
}