package com.norbert.koller.student.activities

import android.os.Bundle
import com.norbert.koller.student.R


class MainActivity : com.norbert.koller.shared.activities.MainActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onCreated(savedInstanceState)
    }



}