package com.norbert.koller.shared.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.norbert.koller.shared.R

class InformationCaretakerActivity : ManageActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getContentLayout(): Int {
        return R.layout.fragment_information_caretaker
    }

    override fun getName(): Int {
        return R.string.caretaker
    }


}