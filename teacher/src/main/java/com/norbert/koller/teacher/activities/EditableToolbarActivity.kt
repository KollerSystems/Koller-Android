package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.Style
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.databinding.ActivityToolbarBinding
import com.norbert.koller.teacher.databinding.ActivityEditableToolbarBinding

abstract class EditableToolbarActivity : AppCompatActivity() {

    lateinit var containerBinding : ActivityEditableToolbarBinding

    fun getTitleTil() : TextInputLayout{
        return containerBinding.til
    }

    fun getConfirmButton() : Button {
        return containerBinding.manageBar.button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        containerBinding = ActivityEditableToolbarBinding.inflate(layoutInflater, null, false)
        val content = createContentView()

        containerBinding.scrollView.addView(content)

        setContentView(containerBinding.root)

        val title = getName()
        containerBinding.textTitleFirst.text = title.first
        containerBinding.textTitleLast.text = title.second

        containerBinding.buttonBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
    }

    abstract fun createContentView() : ViewGroup

    abstract fun getName() : Pair<String, String>

}