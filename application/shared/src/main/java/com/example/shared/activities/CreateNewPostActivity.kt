package com.example.shared.activities

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.fragments.bottomsheet.BottomFragmentPostTypes
import com.example.shared.fragments.bottomsheet.ScheduleFragment
import com.example.shared.recycleradapter.EditableImageRecyclerAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker


class CreateNewPostActivity : AppCompatActivity() {

    lateinit var tilAddresse: TextInputLayout
    lateinit var actvAddresse: AutoCompleteTextView
    lateinit var chipsAddresse: ChipGroup
    lateinit var tilTitle: TextInputLayout
    lateinit var tilDescription: TextInputLayout
    var date : Long = 0
    public var scheduleDate : Long = 0
    public var scheduleTime : Int = 0
    val imageLimit = 25

    var imageUris : ArrayList<Uri> = ArrayList()


    fun beforeClose(){
        if(chipsAddresse.childCount > 1 || (tilTitle.editText!!.text?.length ?:0) > 0 || (tilDescription.editText!!.text?.length ?:0) > 0 || imageUris.size > 0){
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.are_you_sure_you_want_to_discard_the_post))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(R.string.no)) { _, _ ->

                }
                .setNeutralButton(getString(R.string.create_a_draft)){ _, _->
                    finish()
                }
                .show()
        }
        else{
            finish()
        }
    }

    lateinit var tilDateFrom : TextInputLayout
    lateinit var tilTimeFrom : TextInputLayout
    lateinit var tilDateTo : TextInputLayout
    lateinit var tilTimeTo : TextInputLayout

    lateinit var dpdFrom : MaterialDatePicker<Long>
    lateinit var dpdTo : MaterialDatePicker<Long>
    lateinit var tpdFrom : MaterialTimePicker
    lateinit var tpdTo : MaterialTimePicker

    lateinit var textViewImageLimit : TextView

    private fun setupDbd(til : TextInputLayout, unlockTils : ArrayList<TextInputLayout>, lockTils : ArrayList<TextInputLayout>) : MaterialDatePicker<Long>{
        var dpd = MaterialDatePicker.Builder.datePicker()
            .build()

        til.isEndIconVisible = false

        for (lockTil in lockTils){
            lockTil.isEnabled = false
        }

        dpd.addOnPositiveButtonClickListener {selection ->

            til.editText!!.setText(MyApplication.simpleLocalShortMonthDay.format(selection))

            til.isEndIconVisible = true

            val tils : ArrayList<TextInputLayout> = if(tilTimeFrom.editText!!.text.isEmpty()){
                unlockTils
            } else{
                lockTils
            }
            for (unlockTil in tils){
                unlockTil.isEnabled = true
            }
        }

        return dpd
    }


    private fun setupTbd(til : TextInputLayout, lockTil : TextInputLayout?) : MaterialTimePicker{
        var tpd = MaterialTimePicker.Builder()
            .build()

        til.isEndIconVisible = false

        lockTil?.isEnabled = false

        tpd.addOnPositiveButtonClickListener {

            til.editText!!.setText(MyApplication.timeToString(tpd.hour, tpd.minute))

            til.isEndIconVisible = true

            lockTil?.isEnabled = true
        }

        return tpd
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_post)

        val bottomView : View = findViewById(R.id.bottom_view)
        bottomView.post{
            val navViewColor = MyApplication.getPixelColorFromView(bottomView, 0, 0)
            window.navigationBarColor = navViewColor
        }

        val publishButton: Button = findViewById (R.id.create_new_post_button_publish)
        val scheduleButton: Button = findViewById (R.id.create_new_post_button_scheduling)
        val buttonExit: Button = findViewById(R.id.toolbar_exit)

        tilAddresse = findViewById (R.id.create_new_post_til_addresse)
        actvAddresse = findViewById (R.id.create_new_post_edt_addresse)
        chipsAddresse = findViewById (R.id.create_new_post_chips_addresse)
        tilTitle = findViewById (R.id.create_new_post_til_title)
        tilDescription = findViewById (R.id.create_new_post_til_description)

        tilDateFrom = findViewById(R.id.create_new_post_til_date_from)
        tilTimeFrom = findViewById(R.id.create_new_post_til_time_from)
        tilDateTo = findViewById(R.id.create_new_post_til_date_to)
        tilTimeTo = findViewById(R.id.create_new_post_til_time_to)

        textViewImageLimit = findViewById(R.id.create_new_post_text_view_image_limit)

        val cardDateTime : MaterialCardView = findViewById (R.id.create_new_post_card_datetime)
        val cardPlace : MaterialCardView = findViewById (R.id.create_new_post_card_place)
        val cardBaseProgram : MaterialCardView = findViewById (R.id.create_new_post_card_base_program)


        buttonExit.setOnClickListener{
            beforeClose()
        }

        val unlock : ArrayList<TextInputLayout> = arrayListOf(tilTimeFrom, tilDateTo)
        val lock : ArrayList<TextInputLayout> = arrayListOf(tilTimeFrom, tilDateTo, tilTimeTo)

        dpdFrom = setupDbd(tilDateFrom, unlock, lock)
        tilDateFrom.setEndIconOnClickListener{
            tilDateFrom.editText!!.setText("")
            dpdFrom = setupDbd(tilDateFrom, unlock, lock)
        }

        dpdTo = setupDbd(tilDateTo, arrayListOf(), arrayListOf())
        tilDateTo.setEndIconOnClickListener{
            tilDateTo.editText!!.setText("")
            dpdTo = setupDbd(tilDateTo, arrayListOf(), arrayListOf())
        }

        tpdFrom = setupTbd(tilTimeFrom, tilTimeTo)
        tilTimeFrom.setEndIconOnClickListener{
            tilTimeFrom.editText!!.setText("")
            tpdFrom = setupTbd(tilTimeFrom, tilTimeTo)
        }

        tpdTo = setupTbd(tilTimeTo, null)
        tilTimeTo.setEndIconOnClickListener{
            tilTimeTo.editText!!.setText("")
            tpdTo = setupTbd(tilTimeTo, null)
        }


        tilDateFrom.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            dpdFrom.show(supportFragmentManager,  "MATERIAL_DATE_PICKER")
        }

        tilDateTo.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            dpdTo.show(supportFragmentManager,  "MATERIAL_DATE_PICKER")
        }

        tilTimeFrom.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            tpdFrom.show(supportFragmentManager,  "MATERIAL_DATE_PICKER")
        }

        tilTimeTo.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            tpdTo.show(supportFragmentManager,  "MATERIAL_DATE_PICKER")
        }


        val tilType: TextInputLayout = findViewById(R.id.create_new_post_til_type)


        fun checkInputsRefreshButton(){
            publishButton.isEnabled = (tilTitle.editText!!.text?.length ?: 0) > 0 && (tilDescription.editText!!.text?.length ?: 0) > 0
            scheduleButton.isEnabled = publishButton.isEnabled
        }

        checkInputsRefreshButton()


        val startBoxStrokeWidth = tilTitle.boxStrokeWidth
        tilAddresse.editText!!.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus) {
                actvAddresse.requestFocus()
            }
        }

        fun setAddressesToEveryoneIfEmpty(){
            if((actvAddresse.text.length ?:0)==0 && chipsAddresse.childCount <= 1){
                tilAddresse.editText!!.setText(getString(R.string.everyone))
            }
        }

        actvAddresse.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus){
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(actvAddresse, InputMethodManager.SHOW_IMPLICIT)
                tilAddresse.editText!!.setText(" ")
                tilAddresse.boxStrokeWidth = tilAddresse.boxStrokeWidthFocused
                tilAddresse.error = " "
            } else
            {
                setAddressesToEveryoneIfEmpty()
                tilAddresse.boxStrokeWidth = startBoxStrokeWidth
                tilAddresse.error = null
            }
        }

        val addresseItems = listOf("Nagy Géza", "Andrásosfi Norberto", "Kovács Gábor", "Nagy Norbert", "Lányok", "Fiúk", "F1", "F2", "F3", "L1", "L2", "L3")
        val addresseAdapter = ArrayAdapter(this, R.layout.list_item_text, addresseItems)

        actvAddresse.setAdapter(addresseAdapter)

        fun removeChip(chip : Chip){
            chipsAddresse.removeView(chip)
        }

        actvAddresse.setOnItemClickListener { parent, view, position, id ->
            val chip = Chip(this)
            chip.text = actvAddresse.text
            actvAddresse.text = null
            chip.isCheckable = false
            chip.isCloseIconVisible = true
            chip.ensureAccessibleTouchTarget(0)
            chip.setOnCloseIconClickListener {
                removeChip(chip)
                setAddressesToEveryoneIfEmpty()
            }
            chipsAddresse.addView(chip, chipsAddresse.childCount-1)
        }

        tilTitle.requestFocus()


        actvAddresse.setOnKeyListener { _, keyCode, event ->
            var currentChip: Chip? = null
            if (chipsAddresse.childCount > 1) {
                currentChip = (chipsAddresse.getChildAt(chipsAddresse.childCount - 1 - 1) as Chip)
                if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && actvAddresse.text.isNullOrEmpty()) {
                    if (!currentChip.isChecked) {
                        currentChip.isCheckable = true
                        currentChip.isChecked = true
                        currentChip.isCheckable = false
                    } else {
                        removeChip(currentChip)
                    }
                }
            }



            true
        }

        actvAddresse.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


                if (chipsAddresse.childCount > 1) {
                    val currentChip = (chipsAddresse.getChildAt(chipsAddresse.childCount - 1 - 1) as Chip)
                    currentChip.isCheckable = true
                    currentChip.isChecked = false
                    currentChip.isCheckable = false
                }

            }
        })


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


        tilType.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            val dialog = BottomFragmentPostTypes()

            dialog.show(supportFragmentManager, BottomFragmentPostTypes.TAG)

            tilType.post(Runnable {
                dialog.requireView().findViewById<View>(R.id.post_type_ly_post).setOnClickListener{
                    tilType.editText!!.setText(getString(R.string.general_post))
                    dialog.dismiss()
                }
                dialog.requireView().findViewById<View>(R.id.post_type_ly_program).setOnClickListener{
                    tilType.editText!!.setText(getString(R.string.program))
                    dialog.dismiss()
                }
                dialog.requireView().findViewById<View>(R.id.post_type_ly_news).setOnClickListener{
                    tilType.editText!!.setText(getString(R.string.news_one))
                    dialog.dismiss()
                }
            })

        }


        tilType.editText!!.addTextChangedListener {
            when (tilType.editText!!.text.toString()) {
                getString(R.string.news_one) -> {
                    cardDateTime.visibility = VISIBLE
                    cardPlace.visibility = VISIBLE
                    cardBaseProgram.visibility = GONE
                }
                getString(R.string.program) -> {
                    cardDateTime.visibility = VISIBLE
                    cardPlace.visibility = VISIBLE
                    cardBaseProgram.visibility = VISIBLE
                }
                getString(R.string.general_post) -> {
                    cardDateTime.visibility = GONE
                    cardPlace.visibility = VISIBLE
                    cardBaseProgram.visibility = GONE
                }
                else -> {

                }
            }
        }

        if(intent.extras != null)
            tilType.editText!!.setText(intent.extras!!.getString("type"))


        val imageRecyclerView : RecyclerView = findViewById(R.id.recycler_view)

        imageRecyclerView.setHasFixedSize(true)


        imageRecyclerView.adapter = EditableImageRecyclerAdapter(imageUris, this@CreateNewPostActivity, imageLimit, textViewImageLimit)

        fun checkImages() : Boolean{
            if(imageUris.size > imageLimit){
                if(!textViewImageLimit.text.contains(" • ")) {
                    textViewImageLimit.text =
                        getString(R.string.too_many_images) + " • " + textViewImageLimit.text
                }
                return false
            }
            return true
        }

        publishButton.setOnClickListener{
            if(checkImages()) {
                finish()
            }
        }

        scheduleButton.setOnClickListener{
            if(checkImages()) {
                val dialog = ScheduleFragment()
                dialog.show(supportFragmentManager, ScheduleFragment.TAG)
            }
        }
    }

    override fun onBackPressed() {
        beforeClose()
    }
}