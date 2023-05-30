package com.example.koller.activities

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.widget.addTextChangedListener
import com.example.koller.MyApplication
import com.example.koller.R
import com.example.koller.fragments.bottomsheet.ScheduleFragment
import com.example.koller.fragments.bottomsheet.BottomFragmentPostTypes
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout


class CreateNewPostActivity : AppCompatActivity() {

    lateinit var tilAddresse: TextInputLayout
    lateinit var actvAddresse: AutoCompleteTextView
    lateinit var chipsAddresse: ChipGroup
    lateinit var tilTitle: TextInputLayout
    lateinit var tilDescription: TextInputLayout
    lateinit var cardDate: View
    lateinit var buttonAddImage : Button
    var date : Long = 0
    public var scheduleDate : Long = 0
    public var scheduleTime : Int = 0
    lateinit var scrollViewImage : HorizontalScrollView


    fun beforeClose(){
        if(chipsAddresse.childCount > 1 || (tilTitle.editText!!.text?.length ?:0) > 0 || (tilDescription.editText!!.text?.length ?:0) > 0){
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

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {

            val shapeableImageView = ShapeableImageView(ContextThemeWrapper(this,
                R.style.ImagePreviewList
            ))
            shapeableImageView.scaleType = ImageView.ScaleType.CENTER_CROP

            val layoutParams = ViewGroup.MarginLayoutParams(
                MyApplication.convertDpToPixel(150, this),
                MyApplication.convertDpToPixel(150, this)
            )
            val marginInDp = MyApplication.convertDpToPixel(5, this)
            layoutParams.setMargins(marginInDp, marginInDp, marginInDp, marginInDp)


            shapeableImageView.layoutParams = layoutParams

            shapeableImageView.setImageURI(uri)
            val parent : LinearLayout = (buttonAddImage.parent as LinearLayout)
            parent.addView(shapeableImageView, parent.childCount-1)
            scrollViewImage.post {
                scrollViewImage.scrollTo(scrollViewImage.getChildAt(0).width, 0)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_post)
        scrollViewImage = findViewById(R.id.create_new_post_scroll_view_images)
        val publishButton: Button = findViewById (R.id.create_new_post_button_publish)
        val scheduleButton: Button = findViewById (R.id.create_new_post_button_scheduling)
        val buttonExit: Button = findViewById(R.id.toolbar_exit)
        val buttonRemoveDate: Button = findViewById(R.id.create_new_post_button_remove_date)
        buttonAddImage = findViewById(R.id.create_new_post_button_add_image)

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

        fun setAddressesToEveryoneIfEmpty(){
            if((actvAddresse.text.length ?:0)==0 && chipsAddresse.childCount <= 1){
                tilAddresse.editText!!.setText(getString(R.string.everyone))
            }
        }

        actvAddresse.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus){
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

        }

        if(intent.extras != null)
            tilType.editText!!.setText(intent.extras!!.getString("type"))

        buttonAddImage.setOnClickListener{


            launcher.launch("image/*")
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