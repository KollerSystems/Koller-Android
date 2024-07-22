package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentDataCloseRelativeBinding

class EditCloseRelativeActivity : ManageActivity(){

    lateinit var contentBinding : ContentDataCloseRelativeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getContentView(): ViewGroup {
        contentBinding = ContentDataCloseRelativeBinding.inflate(layoutInflater)
        return contentBinding.root
    }

    override fun getName(): Int {
        return R.string.close_relative
    }

}