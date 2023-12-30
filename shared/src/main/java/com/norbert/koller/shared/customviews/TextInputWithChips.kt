package com.norbert.koller.shared.customviews

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.size
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.R

class TextInputWithChips(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs)  {

    private lateinit var tilAddresse: TextInputLayout
    private lateinit var actvAddresse: AutoCompleteTextView
    lateinit var chipsAddresse: ChipGroup

    var onChipChange : (() -> Unit)? = null
    
    fun emptyAddresses(){
        if((actvAddresse.text.length)==0 && !containsChip() && !actvAddresse.isFocused){
            tilAddresse.editText!!.setText("")
        }
    }

    fun containsChip() : Boolean{
        return (chipsAddresse.size > 1)
    }
    
    init {
        this.inflate()

        tilAddresse = findViewById (R.id.create_new_post_til_addresse)
        actvAddresse = findViewById (R.id.create_new_post_edt_addresse)
        chipsAddresse = findViewById (R.id.create_new_post_chips_addresse)

        val startBoxStrokeWidth = tilAddresse.boxStrokeWidth
        tilAddresse.editText!!.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus) {
                actvAddresse.requestFocus()
            }
        }


        actvAddresse.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus){
                val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(actvAddresse, InputMethodManager.SHOW_IMPLICIT)
                tilAddresse.editText!!.setText(" ")
                tilAddresse.boxStrokeWidth = tilAddresse.boxStrokeWidthFocused
                tilAddresse.error = " "
            } else
            {
                emptyAddresses()
                tilAddresse.boxStrokeWidth = startBoxStrokeWidth
                tilAddresse.error = null
            }
        }

        val addresseItems = listOf("Nagy Géza", "Andrásosfi Norberto", "Kovács Gábor", "Nagy Norbert", "Lányok", "Fiúk", "F1", "F2", "F3", "L1", "L2", "L3")
        val addresseAdapter = ArrayAdapter(context, R.layout.view_list_item_text, addresseItems)

        actvAddresse.setAdapter(addresseAdapter)

        fun removeChip(chip : Chip){
            chipsAddresse.removeView(chip)
            onChipChange?.invoke()
        }

        actvAddresse.setOnItemClickListener { parent, view, position, id ->
            val chip = Chip(context)
            chip.text = actvAddresse.text
            actvAddresse.text = null
            chip.isCheckable = false
            chip.isCloseIconVisible = true
            chip.ensureAccessibleTouchTarget(0)
            chip.setOnCloseIconClickListener {
                removeChip(chip)
                emptyAddresses()
            }
            chipsAddresse.addView(chip, chipsAddresse.childCount-1)
            onChipChange?.invoke()
        }


        actvAddresse.setOnKeyListener { _, keyCode, event ->
            var currentChip: Chip?
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
    }

    open fun inflate(){
        View.inflate(context, R.layout.text_input_with_chips, this)
    }


}