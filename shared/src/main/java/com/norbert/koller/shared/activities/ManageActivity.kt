package com.norbert.koller.shared.activities

import android.app.Activity
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

abstract class ManageActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val inflater : LayoutInflater = LayoutInflater.from(this);

        val conatiner : ViewGroup = (inflater.inflate(R.layout.activity_manage, null, false) as ViewGroup)

        val content : View = inflater.inflate(getContentLayout(), null, false)
        val layoutParams = CoordinatorLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()
        content.layoutParams = layoutParams

        (conatiner.getChildAt(0) as ViewGroup).addView(content)

        setContentView(conatiner)

        val toolbar : CollapsingToolbarLayout = findViewById(R.id.CollapsingToolbarLayout)

        toolbar.title = getString(getName())

        val appBarLayout : com.norbert.koller.shared.customviews.AppBarLayout = findViewById(R.id.appbar)

        appBarLayout.backButton.setOnClickListener{
            finish()
        }

    }

    abstract fun getContentLayout() : Int

    abstract fun getName() : Int

}