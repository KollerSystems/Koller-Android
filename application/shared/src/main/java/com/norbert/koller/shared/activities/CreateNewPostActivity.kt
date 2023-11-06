package com.norbert.koller.shared.activities

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
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.bottomsheet.BottomFragmentPostTypes
import com.norbert.koller.shared.fragments.bottomsheet.ScheduleFragment
import com.norbert.koller.shared.getColorOfPixel
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.recycleradapter.EditableImageRecyclerAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.TimeZone


class CreateNewPostActivity : AppCompatActivity() {

    private lateinit var tilAddresse: TextInputLayout
    private lateinit var actvAddresse: AutoCompleteTextView
    lateinit var chipsAddresse: ChipGroup
    private lateinit var tilTitle: TextInputLayout
    private lateinit var tilDescription: TextInputLayout

    var scheduleDate : Long = 0
    var scheduleTime : Int = 0
    private val imageLimit = 25

    private var imageUris : ArrayList<Uri> = ArrayList()


    private fun beforeClose(){
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

    private lateinit var tilDateFrom : TextInputLayout
    private lateinit var tilTimeFrom : TextInputLayout
    private lateinit var tilDateTo : TextInputLayout
    private lateinit var tilTimeTo : TextInputLayout

    private lateinit var textViewImageLimit : TextView



    private fun setupTbd(til : TextInputLayout) : MaterialTimePicker{


        val time : Pair<Int, Int> = DateTimeHelper.timeFromString(til.editText!!.text.toString())
        val tpd = MaterialTimePicker.Builder()
            .setHour(time.first).setMinute(time.second)
            .build()

        tpd.addOnPositiveButtonClickListener {

            til.editText!!.setText(DateTimeHelper.timeToString(tpd.hour, tpd.minute))

            til.isEndIconVisible = true

        }

        return tpd
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_post)

        val bottomView : View = findViewById(R.id.bottom_view)
        bottomView.post{
            val navViewColor = bottomView.getColorOfPixel(0, 0)
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
        tilDateFrom.isEndIconVisible = false
        tilTimeFrom = findViewById(R.id.create_new_post_til_time_from)
        tilTimeFrom.isEndIconVisible = false
        tilDateTo = findViewById(R.id.create_new_post_til_date_to)
        tilDateTo.isEndIconVisible = false
        tilTimeTo = findViewById(R.id.create_new_post_til_time_to)
        tilTimeTo.isEndIconVisible = false

        textViewImageLimit = findViewById(R.id.create_new_post_text_view_image_limit)

        val cardDateTime : MaterialCardView = findViewById (R.id.create_new_post_card_datetime)
        val cardPlace : MaterialCardView = findViewById (R.id.create_new_post_card_place)
        val cardBaseProgram : MaterialCardView = findViewById (R.id.create_new_post_card_base_program)


        buttonExit.setOnClickListener{
            beforeClose()
        }

        tilDateFrom.setEndIconOnClickListener{
            tilDateFrom.editText!!.setText("")
            tilDateFrom.tag = 0
            tilDateFrom.isEndIconVisible = false
        }

        tilDateTo.setEndIconOnClickListener{
            tilDateTo.editText!!.setText("")
            tilDateTo.tag = 0
            tilDateTo.isEndIconVisible = false
        }

        tilTimeFrom.setEndIconOnClickListener{
            tilTimeFrom.editText!!.setText("")
            tilTimeFrom.isEndIconVisible = false
        }

        tilTimeTo.setEndIconOnClickListener{
            tilTimeTo.editText!!.setText("")
            tilTimeTo.isEndIconVisible = false
        }


        tilDateFrom.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            val dpd = DateTimeHelper.setupDbd(tilDateFrom.editText!!)
            dpd.addOnPositiveButtonClickListener {
                tilDateFrom.isEndIconVisible = true
            }
            dpd.show(supportFragmentManager,  "MATERIAL_DATE_PICKER")
        }

        tilDateTo.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            val dpd = DateTimeHelper.setupDbd(tilDateTo.editText!!)
            dpd.addOnPositiveButtonClickListener {
                tilDateTo.isEndIconVisible = true
            }
            dpd.show(supportFragmentManager,  "MATERIAL_DATE_PICKER")
        }

        tilTimeFrom.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            setupTbd(tilTimeFrom).show(supportFragmentManager,  "MATERIAL_DATE_PICKER")
        }

        tilTimeTo.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            setupTbd(tilTimeTo).show(supportFragmentManager,  "MATERIAL_DATE_PICKER")
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
            if((actvAddresse.text.length)==0 && chipsAddresse.childCount <= 1){
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
        val addresseAdapter = ArrayAdapter(this, R.layout.view_list_item_text, addresseItems)

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
                        "${getString(R.string.too_many_images)} • ${textViewImageLimit.text}"
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