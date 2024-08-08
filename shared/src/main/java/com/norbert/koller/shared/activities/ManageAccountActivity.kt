package com.norbert.koller.shared.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ActivityManageAccountBinding
import com.norbert.koller.shared.managers.ApplicationManager
import com.stfalcon.imageviewer.StfalconImageViewer

class ManageAccountActivity : ToolbarActivity() {

    private lateinit var binding : ActivityManageAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.textName.text = UserData.instance.name
        binding.user.setUser(UserData.instance)

        binding.user.setOnClickListener{
            StfalconImageViewer.Builder(this, listOf(binding.user.getImage().drawable)){ view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withTransitionFrom(binding.user.getImage())
                .show(this.supportFragmentManager)
        }

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

        binding.cbPassword.setOnClickListener{
            ApplicationManager.openActivity(this, ChangePasswordActivity::class.java)
        }
    }

    override fun createContentView(): ViewGroup {
        binding = ActivityManageAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getName(): String {
        return getString(R.string.manage_account)
    }
}