package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.R

class RangeInputFragment(val defaultRange : Pair<Int?, Int?>) : BottomSheetDialogFragment() {

    lateinit var tilFrom : TextInputLayout
    lateinit var tilTo : TextInputLayout
    lateinit var getValuesOnFinish: ((range : Pair<Int?, Int?>) -> Unit)
    var userChanged : Boolean = false
    var beingCopied : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bshd_range_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tilFrom = view.findViewById(R.id.til_from)
        tilTo = view.findViewById(R.id.til_to)

        if(defaultRange.first != null) {
            tilFrom.editText!!.setText(defaultRange.first!!.toString())
            tilTo.editText!!.setText(defaultRange.second!!.toString())

            userChanged = tilFrom.editText!!.text.toString() != tilTo.editText!!.text.toString()
        }

        tilFrom.editText!!.doOnTextChanged{text,_,_,_ ->
            if(!userChanged){
                beingCopied = true
                tilTo.editText!!.setText(text)
            }
        }

        tilTo.editText!!.doOnTextChanged{text,_,_,_ ->

            if(!beingCopied){
                userChanged = !text.isNullOrBlank()
            }

            beingCopied = false
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        var fromInt : Int? = tilFrom.editText!!.text.toString().toIntOrNull()
        var toInt : Int? = tilTo.editText!!.text.toString().toIntOrNull()

        if(fromInt == null && toInt != null){
            fromInt = 0
        }
        else if(fromInt != null && toInt == null){
            toInt = fromInt
        }
        else if (fromInt != null && toInt != null && fromInt > toInt) {
            toInt = fromInt
        }

        getValuesOnFinish.invoke(Pair(fromInt, toInt))
    }


    companion object {
        const val TAG = "RangeInputBottomSheet"
    }
}