package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.norbert.koller.shared.databinding.ActivityToolbarBinding

abstract class ToolbarActivity : AppCompatActivity() {

    lateinit var containerBinding : ActivityToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        containerBinding = ActivityToolbarBinding.inflate(layoutInflater, null, false)
        val content = createContentView()

        containerBinding.scrollView.addView(content)

        setContentView(containerBinding.root)

        containerBinding.collapsingToolbar.title = getName()

        containerBinding.buttonBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
    }

    abstract fun createContentView() : ViewGroup

    abstract fun getName() : String
}