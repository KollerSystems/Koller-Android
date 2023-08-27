package com.norbert.koller.shared.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.recycleradapter.EditableImageRecyclerAdapter
import com.norbert.koller.shared.recycleradapter.TodayRecyclerAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

class FeedbackActivity : AppCompatActivity() {

    lateinit var tilTitle: TextInputLayout
    lateinit var tilDescription: TextInputLayout
    val imageLimit = 50

    var imageUris : ArrayList<Uri> = ArrayList()

    fun beforeClose(){
        if((tilTitle.editText!!.text?.length ?:0) > 0 || (tilDescription.editText!!.text?.length ?:0) > 0 || imageUris.size > 0){
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.are_you_sure_you_want_to_discard_the_post))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(R.string.no)) { _, _ ->

                }
        }
        else{
            finish()
        }
    }

    lateinit var textViewImageLimit : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val sendButton: Button = findViewById (R.id.button_send)
        val buttonExit: Button = findViewById(R.id.toolbar_exit)
        
        tilTitle = findViewById (R.id.til_title)
        tilDescription = findViewById (R.id.til_description)

        textViewImageLimit = findViewById(R.id.text_view_image_limit)

        buttonExit.setOnClickListener{
            beforeClose()
        }

        fun checkInputsRefreshButton(){
            sendButton.isEnabled = (tilTitle.editText!!.text?.length ?: 0) > 0 && (tilDescription.editText!!.text?.length ?: 0) > 0
        }


        val recyclerViewAlready : RecyclerView = findViewById(R.id.recycler_view_already)

        recyclerViewAlready.layoutManager = LinearLayoutManager(this)
        recyclerViewAlready.setHasFixedSize(true)

        val arrayListAlready = arrayListOf(
            TodayData(getDrawable(R.drawable.pending), "Furcsán mozogó kép a hozzáadás gomb fölé való húzáskor", getString(R.string.pending)),
            TodayData(getDrawable(R.drawable.pending), "Nem lehet a hír tartalmát a fejlécnél fogva görgetni", getString(R.string.pending)),
            TodayData(getDrawable(R.drawable.block), "Túl kevés a fekete felhasználó", getString(R.string.not_a_bug) + " • Ezzel mi nem tudunk mit kezdeni."),
            TodayData(getDrawable(R.drawable.pending), "Nincs elkülönítő körvonal X helyen", getString(R.string.pending)),
            TodayData(getDrawable(R.drawable.block), "Szar a WC", getString(R.string.not_a_bug) + " • Ezt kérlek a kollégimnak jelezd.")
            )

        recyclerViewAlready.adapter = TodayRecyclerAdapter(arrayListAlready, this)


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


        val imageRecyclerView : RecyclerView = findViewById(R.id.recycler_view)

        imageRecyclerView.setHasFixedSize(true)

        imageRecyclerView.adapter = EditableImageRecyclerAdapter(imageUris, this, imageLimit, textViewImageLimit)

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

        sendButton.setOnClickListener{
            if(checkImages()) {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        beforeClose()
    }
}