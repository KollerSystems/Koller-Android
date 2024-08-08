package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentDataStudentBinding

class EditStudentActivity : ManageActivity(){

    lateinit var contentBinding : ContentDataStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun createContentView(): ViewGroup {
        contentBinding = ContentDataStudentBinding.inflate(layoutInflater)
        return contentBinding.root
    }

    override fun getName(): String {
        return getString(R.string.student)
    }


}