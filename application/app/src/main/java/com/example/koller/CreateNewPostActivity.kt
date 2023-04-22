package com.example.koller

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


class CreateNewPostActivity : AppCompatActivity() {

    lateinit var tilAddresse: TextInputLayout
    lateinit var tilTitle: TextInputLayout
    lateinit var tilDescription: TextInputLayout
    lateinit var cardDate: View
    var date : Long = 0
    public var scheduleDate : Long = 0
    public var scheduleTime : Int = 0


    fun beforeClose(){
        if((tilAddresse.editText!!.text?.length ?:0) > 0 || (tilTitle.editText!!.text?.length ?:0) > 0 || (tilDescription.editText!!.text?.length ?:0) > 0){
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.q_save_a_draft))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(R.string.no)) { _, _ ->
                    finish()
                }
                .setNeutralButton(getString(R.string.continue_editing)){_,_->

                }
                .show()
        }
        else{
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_post)


        val publishButton: Button = findViewById (R.id.create_new_post_button_publish)
        val scheduleButton: Button = findViewById (R.id.create_new_post_button_scheduling)
        val buttonExit: Button = findViewById(R.id.toolbar_exit)
        val buttonRemoveDate: Button = findViewById(R.id.create_new_post_button_remove_date)
        val buttonAddImage : Button = findViewById(R.id.create_new_post_button_add_image)

        tilAddresse = findViewById (R.id.create_new_post_til_addresse)
        tilTitle = findViewById (R.id.create_new_post_til_title)
        tilDescription = findViewById (R.id.create_new_post_til_description)

        val tilBaseProgram : TextInputLayout = findViewById (R.id.create_new_post_til_base_program)
        val tilPlace : TextInputLayout = findViewById (R.id.create_new_post_til_place)

        val textDate : TextView = findViewById(R.id.create_new_post_text_date)

        buttonExit.setOnClickListener{
            beforeClose()
        }

        val dpd =  MaterialDatePicker.Builder.datePicker()
            .build()

        cardDate = findViewById(R.id.create_new_post_card_date_time)
        cardDate.setOnClickListener{


            dpd.show(supportFragmentManager,  "MATERIAL_DATE_PICKER")

        }

        dpd.addOnPositiveButtonClickListener{

            textDate.text = dpd.headerText
            date = it
            buttonRemoveDate.visibility = VISIBLE
        }

        buttonRemoveDate.setOnClickListener{
            textDate.text = getString(R.string.unset)
            buttonRemoveDate.visibility = GONE
        }


        findViewById<View>(R.id.create_new_post_card_place).setOnClickListener{
            tilPlace.requestFocus()
        }

        findViewById<View>(R.id.create_new_post_card_base_program).setOnClickListener{
            tilBaseProgram.requestFocus()
        }

        val tilType: TextInputLayout = findViewById(R.id.create_new_post_til_type)


        fun checkInputsRefreshButton(){
            publishButton.isEnabled = (tilTitle.editText!!.text?.length ?: 0) > 0 && (tilDescription.editText!!.text?.length ?: 0) > 0
            scheduleButton.isEnabled = publishButton.isEnabled
        }

        checkInputsRefreshButton()

        tilAddresse.requestFocus()



        tilTitle.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
            }
        })

        tilDescription.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
            }
        })

        val items = listOf(getString(R.string.general_post), getString(R.string.program), getString(R.string.news_one))
        val adapter = ArrayAdapter(this, R.layout.list_item_text, items)

        val acText = (tilType.editText as? AutoCompleteTextView)!!

        acText.setAdapter(adapter)

        val sizeText : TextView = findViewById(R.id.create_new_post_text_extra)

        acText.addTextChangedListener {
            sizeText.text = acText.text

            acText.post(Runnable {
                acText.setLayoutParams(FrameLayout.LayoutParams(sizeText.width, FrameLayout.LayoutParams.WRAP_CONTENT))
            })
        }

        buttonAddImage.setOnClickListener{

        }

        publishButton.setOnClickListener{
            finish()
        }

        scheduleButton.setOnClickListener{
            val dialog = ScheduleFragment()
            dialog.show(supportFragmentManager, ScheduleFragment.TAG)
        }
    }

    override fun onBackPressed() {
        beforeClose()
    }
}