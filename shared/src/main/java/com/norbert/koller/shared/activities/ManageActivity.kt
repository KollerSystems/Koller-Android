package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ActivityManageBinding

abstract class ManageActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val containerBinding = ActivityManageBinding.inflate(layoutInflater, null, false)
        val content = getContentView()


        val layoutParams = CoordinatorLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()
        content.layoutParams = layoutParams

        (containerBinding.root.getChildAt(0) as ViewGroup).addView(content)

        setContentView(containerBinding.root)

        containerBinding.collapsingToolbar.title = getString(getName())

        containerBinding.buttonBack.setOnClickListener{
            finish()
        }

    }

    abstract fun getContentView() : ViewGroup

    abstract fun getName() : Int

}