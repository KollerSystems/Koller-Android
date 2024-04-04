package com.norbert.koller.teacher.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.Shapeable
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.managers.setToolbarToViewColor
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.teacher.R

class UserPresenceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_presence)

        val appBar : AppBarLayout = findViewById(R.id.appbar)

        appBar.setup()

        window.setToolbarToViewColor(findViewById(R.id.bottom_view))

        val backButton : Button = findViewById(R.id.toolbar_exit)

        val editButton : Button = findViewById(R.id.toolbar_edit)

        val moreButton : MaterialButton = findViewById(R.id.Button_more)

        val saveButton : Button = findViewById(R.id.button_publish_all)

        val editLy : LinearLayout = findViewById(R.id.Ly_edit)
        val readLy : LinearLayout = findViewById(R.id.Ly_read)

        val teacherButton : Button = findViewById(R.id.button_teacher)

        val teacherTil : TextInputLayout = findViewById(R.id.til_teacher)

        val radius = teacherTil.boxCornerRadiusTopStart

        val shapeAppearanceRight = ShapeAppearanceModel.builder(
            this,
            com.norbert.koller.shared.R.style.OverlayRoundedCardRight,
            0
        ).build()

        val shapeAppearanceCorner = ShapeAppearanceModel.builder(
            this,
            com.norbert.koller.shared.R.style.OverlayRoundedCardTopRight,
            0
        ).build()

        fun checkSaveButton(){
            saveButton.isEnabled = !editLy.isVisible
        }

        moreButton.setOnClickListener {
            if(editLy.isVisible){

                fun close(){
                    editLy.isVisible = false
                    readLy.isVisible = true
                    moreButton.text = getString(com.norbert.koller.shared.R.string.edit)
                    moreButton.icon = AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.pencil)
                    checkSaveButton()
                }

                MaterialAlertDialogBuilder(this)
                    .setTitle("Melyik szakköröket szeretné szerkeszteni?")
                    .setPositiveButton("Csak ezt"){_,_->
                        close()
                    }
                    .setNegativeButton("Ezt és az összes ezt követőt"){_,_->
                        close()
                    }
                    .setNeutralButton(getString(com.norbert.koller.shared.R.string.cancel)){_,_->

                    }.show()


            }
            else{
                editLy.isVisible = true
                readLy.isVisible = false
                moreButton.text = getString(com.norbert.koller.shared.R.string.done)
                moreButton.icon = AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.check)
                checkSaveButton()
            }
        }

        backButton.setOnClickListener{
            onBackPressed()
        }

        editButton.setOnClickListener {
            val intent = Intent(this, EditStudyGroupActivity::class.java)
            startActivity(intent)
        }
    }

}