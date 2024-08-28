package com.norbert.koller.teacher.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.activities.EditCaretakerActivity
import com.norbert.koller.shared.activities.EditCloseRelativeActivity
import com.norbert.koller.shared.activities.EditSchoolActivity
import com.norbert.koller.shared.activities.EditStudentActivity
import com.norbert.koller.shared.activities.ManageActivity
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.databinding.ContentActivityEditUserBinding

class EditUserActivity : ManageActivity() {

    lateinit var binding : ContentActivityEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.cbStudent.setOnClickListener{
            ApplicationManager.openActivity(this, EditStudentActivity::class.java)
        }
        binding.cbCaretaker.setOnClickListener{
            ApplicationManager.openActivity(this, EditCaretakerActivity::class.java)
        }
        binding.cbCloseRelative.setOnClickListener{
            ApplicationManager.openActivity(this, EditCloseRelativeActivity::class.java)
        }
        binding.cbSchool.setOnClickListener{
            ApplicationManager.openActivity(this, EditSchoolActivity::class.java)
        }
    }

    override fun createContentView(): ViewGroup {
        binding = ContentActivityEditUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getName(): String {
        return getString(R.string.edit_user)
    }
}