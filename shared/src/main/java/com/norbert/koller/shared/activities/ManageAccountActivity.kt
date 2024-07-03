package com.norbert.koller.shared.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.RoundedBadgeImageView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.setup
import com.stfalcon.imageviewer.StfalconImageViewer

class ManageAccountActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_account)

        val backButton : Button = findViewById(R.id.toolbar_exit)

        backButton.setOnClickListener{
            finish()
        }

        findViewById<AppBarLayout>(R.id.appbar).setup()

        val textName : TextView = findViewById(R.id.text_name)
        textName.text = UserData.instance.name
        val profileBadge : RoundedBadgeImageView = findViewById(R.id.rbiv)
        profileBadge.setUser(UserData.instance)

        profileBadge.setOnClickListener{
            StfalconImageViewer.Builder(this, listOf(profileBadge.image.drawable)){ view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withTransitionFrom(profileBadge.image)
                .show(this.supportFragmentManager)
        }

        val scbStudent : SimpleCardButton = findViewById(R.id.scb_student)
        scbStudent.setOnClickListener{

        }

        val scbCaretaker : SimpleCardButton = findViewById(R.id.scb_caretaker)
        scbCaretaker.setOnClickListener{

        }

        val scbCloseRelative : SimpleCardButton = findViewById(R.id.scb_close_relative)
        scbCloseRelative.setOnClickListener{

        }

        val scbSchool : SimpleCardButton = findViewById(R.id.scb_school)
        scbSchool.setOnClickListener{

        }

        val scbPassword : SimpleCardButton = findViewById(R.id.scb_password)
        scbPassword.setOnClickListener{

        }
    }
}