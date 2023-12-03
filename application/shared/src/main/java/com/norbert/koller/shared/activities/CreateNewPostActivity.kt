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
import com.norbert.koller.shared.setToolbarToViewColor
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


    private lateinit var tilDateFrom : TextInputLayout
    private lateinit var tilTimeFrom : TextInputLayout
    private lateinit var tilDateTo : TextInputLayout
    private lateinit var tilTimeTo : TextInputLayout

    private lateinit var textViewImageLimit : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_post)

        val bottomView : View = findViewById(R.id.bottom_view)
        window.setToolbarToViewColor(bottomView)

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

        }

        val tilType: TextInputLayout = findViewById(R.id.create_new_post_til_type)


        fun checkInputsRefreshButton(){
            publishButton.isEnabled = (tilTitle.editText!!.text?.length ?: 0) > 0 && (tilDescription.editText!!.text?.length ?: 0) > 0
            scheduleButton.isEnabled = publishButton.isEnabled
        }

        checkInputsRefreshButton()



        tilTitle.requestFocus()

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
}