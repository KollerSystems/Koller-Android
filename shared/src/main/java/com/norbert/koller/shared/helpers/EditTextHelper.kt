package com.norbert.koller.shared.helpers

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputLayout

class EditTextHelper {
}

@SuppressLint("SetTextI18n")
fun TextInputLayout.connectToDatePicker(fragmentManager : FragmentManager, ){

    isEndIconVisible = false

    setEndIconOnClickListener{
        editText!!.setText("")
        tag = 0
        isEndIconVisible = false
    }

    editText!!.setOnClickListener{
        clearFocus()
        val dpd = DateTimeHelper.setupDbd(editText!!)
        dpd.addOnPositiveButtonClickListener {
            isEndIconVisible = true
        }
        dpd.show(fragmentManager,  "MATERIAL_DATE_PICKER")
    }
}


@SuppressLint("SetTextI18n")
fun TextInputLayout.connectToTimePicker(fragmentManager : FragmentManager, ){

    isEndIconVisible = false

    setEndIconOnClickListener{
        editText!!.setText("")
        tag = 0
        isEndIconVisible = false
    }

    editText!!.setOnClickListener{
        clearFocus()
        val dpd = DateTimeHelper.setupTimePickerDialog(editText!!)
        dpd.addOnPositiveButtonClickListener {
            isEndIconVisible = true
        }
        dpd.show(fragmentManager,  "MATERIAL_TIME_PICKER")
    }
}