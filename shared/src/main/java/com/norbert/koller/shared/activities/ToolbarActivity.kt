package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.databinding.ActivityToolbarBinding

abstract class ToolbarActivity : AppCompatActivity() {

    lateinit var containerBinding : ActivityToolbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        containerBinding = ActivityToolbarBinding.inflate(layoutInflater, null, false)
        val content = createContentView()

        setupContent(content)

        val params = containerBinding.root.getChildAt(1).layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = AppBarLayout.ScrollingViewBehavior()
        setContentView(containerBinding.root)

        containerBinding.collapsingToolbar.title = getName()

        containerBinding.buttonBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    open fun setupContent(content : ViewGroup){
        val contentHolder = createContentHolder()
        containerBinding.root.addView(contentHolder)
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        content.layoutParams = layoutParams
        contentHolder.addView(content)
    }

    open fun createContentHolder() : ViewGroup{
        val nestedScrollView = NestedScrollView(this)
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        nestedScrollView.layoutParams = layoutParams
        return nestedScrollView
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
    }

    abstract fun createContentView() : ViewGroup

    abstract fun getName() : String
}