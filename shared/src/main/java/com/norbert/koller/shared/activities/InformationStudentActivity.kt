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
import com.norbert.koller.shared.R

class InformationStudentActivity : ManageActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getContentLayout(): Int {
        return R.layout.fragment_information_me
    }

    override fun getName(): Int {
        return R.string.student
    }


}