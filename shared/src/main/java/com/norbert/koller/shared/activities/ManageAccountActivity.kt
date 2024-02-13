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

        val cardChangePassword : SimpleCardButton = findViewById(R.id.scb_password)
        cardChangePassword.setOnClickListener{

        }

        val cardManagePersonalData : SimpleCardButton = findViewById(R.id.scb_personal)
        cardManagePersonalData.setOnClickListener{

        }
    }
}