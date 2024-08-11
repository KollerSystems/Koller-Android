package com.norbert.koller.teacher.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.activities.ManageActivity
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.databinding.ContentEditRoomBinding

class EditRoomActivity : ManageActivity() {

    lateinit var binding : ContentEditRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun createContentView(): ViewGroup {
        binding = ContentEditRoomBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getName(): String {
        return getString(R.string.edit_room)
    }
}