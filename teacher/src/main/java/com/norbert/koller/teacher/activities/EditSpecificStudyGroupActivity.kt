package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.activities.ManageActivity
import com.norbert.koller.shared.customviews.NestedCoordinatorLayout
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.customviews.TimeRangeView
import com.norbert.koller.teacher.databinding.ContentActivityEditStudyGroupBinding

open class EditSpecificStudyGroupActivity : ManageActivity() {

    lateinit var binding : ContentActivityEditStudyGroupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    override fun setupContent(content: ViewGroup) {
        containerBinding.root.addView(content)
    }

    override fun createContentView(): ViewGroup {
        binding = ContentActivityEditStudyGroupBinding.inflate(layoutInflater)
        val params = CoordinatorLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        params.behavior = AppBarLayout.ScrollingViewBehavior()
        binding.root.layoutParams = params
        return binding.root
    }

    override fun getName(): String {
        return getString(R.string.edit_study_group)
    }
}