package com.example.teacher.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teacher.R

class MainActivity : com.example.shared.activities.MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMainActivity()
    }
}