package com.example.koller

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


class CreateNewPostActivity : AppCompatActivity() {

    lateinit var tilAddresse: TextInputLayout
    lateinit var actvAddresse: AutoCompleteTextView
    lateinit var chipsAddresse: ChipGroup
    lateinit var tilTitle: TextInputLayout
    lateinit var tilDescription: TextInputLayout
    lateinit var cardDate: View
    var date : Long = 0
    public var scheduleDate : Long = 0
    public var scheduleTime : Int = 0


    fun beforeClose(){
        if(chipsAddresse.childCount > 1 || (tilTitle.editText!!.text?.length ?:0) > 0 || (tilDescription.editText!!.text?.length ?:0) > 0){
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.are_you_sure_you_want_to_discard_the_post))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(R.string.no)) { _, _ ->

                }
                .setNeutralButton(getString(R.string.create_a_draft)){_,_->
                    finish()
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
        actvAddresse = findViewById (R.id.create_new_post_edt_addresse)
        chipsAddresse = findViewById (R.id.create_new_post_chips_addresse)
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


        var startBoxStrokeWidth = tilTitle.boxStrokeWidth
        tilAddresse.editText!!.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus) {
                actvAddresse.requestFocus()
            }
        }

        actvAddresse.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus){
                tilAddresse.editText!!.setText(" ")
                tilAddresse.boxStrokeWidth = tilAddresse.boxStrokeWidthFocused
                tilAddresse.error = " "
            } else
            {
                if((actvAddresse.text.length ?:0)==0 && chipsAddresse.childCount <= 1){
                    tilAddresse.editText!!.setText(null)
                }
                tilAddresse.boxStrokeWidth = startBoxStrokeWidth
                tilAddresse.error = null
            }
        }

        val addresseItems = listOf("Nagy Géza", "Andrásosfi Norberto", "Kovács Gábor", "Nagy Norbert")
        val addresseAdapter = ArrayAdapter(this, R.layout.list_item_text, addresseItems)

        actvAddresse.setAdapter(addresseAdapter)

        actvAddresse.setOnItemClickListener { parent, view, position, id ->
            val chip = Chip(this)
            chip.text = actvAddresse.text
            actvAddresse.text = null
            chip.isCheckable = false
            chip.isCloseIconVisible = true
            chip.setOnCloseIconClickListener {
                chipsAddresse.removeView(chip)
            }
            chipsAddresse.addView(chip, chipsAddresse.childCount-1)
        }

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

        if(intent.extras != null)
            acText.setText(intent.extras!!.getString("type"))

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