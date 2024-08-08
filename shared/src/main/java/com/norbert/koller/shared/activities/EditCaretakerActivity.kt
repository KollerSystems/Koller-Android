package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ContentDataCaretakerBinding

class EditCaretakerActivity : ManageActivity(){

    lateinit var contentBinding : ContentDataCaretakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun createContentView(): ViewGroup {
        contentBinding = ContentDataCaretakerBinding.inflate(layoutInflater)
        return contentBinding.root
    }

    override fun getName(): String {
        return getString(R.string.caretaker)
    }


}