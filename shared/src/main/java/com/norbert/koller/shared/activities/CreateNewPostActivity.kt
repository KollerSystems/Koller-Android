package com.norbert.koller.shared.activities

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.recycleradapters.EditableImageRecyclerAdapter


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
        setContentView(R.layout.content_activity_create_new_post)


        tilAddresse = findViewById (R.id.til)
        tilTitle = findViewById (R.id.til_purpose)
        tilDescription = findViewById (R.id.create_new_post_til_description)

        tilDateFrom = findViewById(R.id.til_date_from)
        tilTimeFrom = findViewById(R.id.til_time_from)
        tilDateTo = findViewById(R.id.til_date_to)
        tilTimeTo = findViewById(R.id.til_time_to)

        textViewImageLimit = findViewById(R.id.create_new_post_text_view_image_limit)

        val cardDateTime : MaterialCardView = findViewById (R.id.create_new_post_card_datetime)
        val cardPlace : MaterialCardView = findViewById (R.id.create_new_post_card_place)
        val cardBaseProgram : MaterialCardView = findViewById (R.id.create_new_post_card_base_program)




        fun checkInputsRefreshButton(){
            /*publishButton.isEnabled = (tilTitle.editText!!.text?.length ?: 0) > 0 && (tilDescription.editText!!.text?.length ?: 0) > 0
            scheduleButton.isEnabled = publishButton.isEnabled*/
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


        /*tilType.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            val dialog = PostTypesBsdFragment()

            dialog.show(supportFragmentManager, PostTypesBsdFragment.TAG)


        }*/


        /*tilType.editText!!.addTextChangedListener {
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
        }*/

        /*if(intent.extras != null)
            tilType.editText!!.setText(intent.extras!!.getString("type"))*/


        val imageRecyclerView : RecyclerView = findViewById(R.id.recycler_view)

        imageRecyclerView.setHasFixedSize(true)


        imageRecyclerView.adapter = EditableImageRecyclerAdapter(imageUris, this@CreateNewPostActivity, imageLimit, textViewImageLimit)

        @SuppressLint("SetTextI18n")
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

        /*publishButton.setOnClickListener{
            if(checkImages()) {
                finish()
            }
        }

        scheduleButton.setOnClickListener{
            if(checkImages()) {
                val dialog = ScheduleBsdFragment()
                dialog.show(supportFragmentManager, ScheduleBsdFragment.TAG)
            }
        }*/
    }
}