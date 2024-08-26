package com.norbert.koller.shared.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.TextInputWithChipsBinding
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.checkByPass
import com.norbert.koller.shared.recycleradapters.SimpleUserRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TextInputWithChips(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs)  {

    val binding = TextInputWithChipsBinding.inflate(LayoutInflater.from(context), this)

    var radii : Float = 0f
    var addresseItems : List<String> = listOf()

    lateinit var addresseAdapter : SimpleUserRecyclerAdapter

    var onChipChange : (() -> Unit)? = null
    
    @SuppressLint("SetTextI18n")
    fun emptyAddresses(){
        if((binding.et.text.length)==0 && !containsChip() && !binding.et.isFocused){
            binding.til.editText!!.setText("")
        }
    }

    fun containsChip() : Boolean{
        return (binding.chips.size > 1)
    }

    fun removeChip(chip : Chip){
        binding.chips.removeView(chip)
        onChipChange?.invoke()
    }

    fun addChip(text : String){
        val chip = Chip(context)
        chip.text = text
        binding.et.text = null
        chip.isCheckable = false
        chip.isCloseIconVisible = true
        chip.ensureAccessibleTouchTarget(0)
        chip.setOnCloseIconClickListener {
            removeChip(chip)
            emptyAddresses()
        }
        binding.chips.addView(chip, binding.chips.childCount-1)
        onChipChange?.invoke()
        uiFilled()
    }

    @SuppressLint("SetTextI18n")
    fun uiFilled(){
        binding.til.editText!!.setText(" ")
    }
    
    init {
        orientation = VERTICAL

        val startBoxStrokeWidth = binding.til.boxStrokeWidth
        binding.til.editText!!.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus) {
                binding.et.requestFocus()
            }
        }


        binding.et.setOnFocusChangeListener{ view, hasFocus ->
            if (hasFocus){
                val imm = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.et, InputMethodManager.SHOW_IMPLICIT)
                uiFilled()
                binding.til.boxStrokeWidth = binding.til.boxStrokeWidthFocused
                binding.til.error = " "
                if(addresseAdapter.userList.isNotEmpty()){
                    showList()
                }
            } else
            {
                emptyAddresses()
                binding.til.boxStrokeWidth = startBoxStrokeWidth
                binding.til.error = null
                disableList()

                if(containsChip()){
                    for (i in 0 until binding.chips.childCount-1){
                        val chip = binding.chips.getChildAt(i) as Chip
                        chip.checkByPass(false)
                    }
                }
            }
        }


        addresseAdapter = SimpleUserRecyclerAdapter(listOf())



        binding.recyclerView.setAdapter(addresseAdapter)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)




        //addChip(binding.et.text.toString())



        binding.et.setOnKeyListener { _, keyCode, event ->
            var currentChip: Chip?
            if (binding.chips.childCount > 1) {
                currentChip = (binding.chips.getChildAt(binding.chips.childCount - 1 - 1) as Chip)
                if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && binding.et.text.isNullOrEmpty()) {
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

        radii = binding.til.boxCornerRadiusTopStart

        binding.et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


                if (binding.chips.childCount > 1) {
                    val currentChip = (binding.chips.getChildAt(binding.chips.childCount - 1 - 1) as Chip)
                    currentChip.isCheckable = true
                    currentChip.isChecked = false
                    currentChip.isCheckable = false
                }

                if(binding.et.text.length >= 2){
                    CoroutineScope(Dispatchers.IO).launch {
                        RetrofitInstance.communicate({RetrofitInstance.api.getUsers(25, 0, filter = "Name:${ApplicationManager.searchApiWithRegex(binding.et.text.toString())},Role:1")}, {

                            val response = it as List<UserData>
                            if(binding.et.text.length >= 2) {


                                if(response.isNotEmpty()) {
                                    addresseAdapter.userList = response

                                    CoroutineScope(Dispatchers.Main).launch {
                                        addresseAdapter.notifyDataSetChanged()
                                        if(binding.et.isFocused) {
                                            showList()
                                        }
                                    }
                                }
                                else{
                                    emptyList()
                                }
                            }

                        }, { error, errorBody ->
                            addresseAdapter.userList = listOf()

                                emptyList()


                        })
                    }

                }
                else{
                    emptyList()
                }

            }
        })
    }

    fun emptyList(){
        addresseAdapter.userList = listOf()
        CoroutineScope(Dispatchers.Main).launch {
            addresseAdapter.notifyDataSetChanged()
            disableList()
        }
    }

    fun disableList() {

        binding.til.setBoxCornerRadii(radii, radii, radii, radii)
        binding.recyclerView.isVisible = false

    }

    fun showList(){
        binding.recyclerView.isVisible = true
        binding.til.setBoxCornerRadii(radii,radii,0f,0f)
    }
}