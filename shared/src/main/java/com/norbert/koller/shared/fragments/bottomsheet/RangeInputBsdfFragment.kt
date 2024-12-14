package com.norbert.koller.shared.fragments.bottomsheet

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.databinding.ContentFragmentBsdfRangeInputBinding

class RangeInputBsdfFragment(val defaultRange : Pair<Int?, Int?>) : BottomSheetDialogFragment() {

    lateinit var binding : ContentFragmentBsdfRangeInputBinding

    lateinit var getValuesOnFinish: ((range : Pair<Int?, Int?>) -> Unit)
    var userChanged : Boolean = false
    var beingCopied : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = ContentFragmentBsdfRangeInputBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(defaultRange.first != null) {
            binding.tilFrom.editText!!.setText(defaultRange.first!!.toString())
            binding.tilTo.editText!!.setText(defaultRange.second!!.toString())

            userChanged = binding.tilFrom.editText!!.text.toString() != binding.tilTo.editText!!.text.toString()
        }

        binding.tilFrom.editText!!.doOnTextChanged{text,_,_,_ ->
            if(!userChanged){
                beingCopied = true
                binding.tilTo.editText!!.setText(text)
            }
        }

        binding.tilTo.editText!!.doOnTextChanged{text,_,_,_ ->

            if(!beingCopied){
                userChanged = !text.isNullOrBlank()
            }

            beingCopied = false
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        var fromInt : Int? = binding.tilFrom.editText!!.text.toString().toIntOrNull()
        var toInt : Int? = binding.tilTo.editText!!.text.toString().toIntOrNull()

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