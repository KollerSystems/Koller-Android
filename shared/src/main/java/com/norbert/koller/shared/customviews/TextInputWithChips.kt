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
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.recycleradapters.SimpleUserRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TextInputWithChips(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs)  {

    private var tilAddresse: TextInputLayout
    private var actvAddresse: EditText

    var radii : Float = 0f
    var addresseItems : List<String> = listOf()

    lateinit var addresseAdapter : SimpleUserRecyclerAdapter

    lateinit var recyclerView : RecyclerView

    var chipsAddresse: ChipGroup

    var onChipChange : (() -> Unit)? = null
    
    fun emptyAddresses(){
        if((actvAddresse.text.length)==0 && !containsChip() && !actvAddresse.isFocused){
            tilAddresse.editText!!.setText("")
        }
    }

    fun containsChip() : Boolean{
        return (chipsAddresse.size > 1)
    }

    fun removeChip(chip : Chip){
        chipsAddresse.removeView(chip)
        onChipChange?.invoke()
    }

    fun addChip(text : String){
        val chip = Chip(context)
        chip.text = text
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
        uiFilled()
    }

    fun uiFilled(){
        tilAddresse.editText!!.setText(" ")
    }
    
    init {
        this.inflate()

        orientation = VERTICAL

        tilAddresse = findViewById (R.id.create_new_post_til_addresse)
        actvAddresse = findViewById (R.id.create_new_post_edt_addresse)
        chipsAddresse = findViewById (R.id.create_new_post_chips_addresse)
        recyclerView = findViewById(R.id.recycler_view)

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
                uiFilled()
                tilAddresse.boxStrokeWidth = tilAddresse.boxStrokeWidthFocused
                tilAddresse.error = " "
                if(addresseAdapter.userList.isNotEmpty()){
                    showList()
                }
            } else
            {
                emptyAddresses()
                tilAddresse.boxStrokeWidth = startBoxStrokeWidth
                tilAddresse.error = null
                disableList()
            }
        }


        addresseAdapter = SimpleUserRecyclerAdapter(listOf())



        recyclerView.setAdapter(addresseAdapter)

        recyclerView.layoutManager = LinearLayoutManager(context)




        //addChip(actvAddresse.text.toString())



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

        radii = tilAddresse.boxCornerRadiusTopStart

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

                if(actvAddresse.text.length >= 2){
                    CoroutineScope(Dispatchers.IO).launch {
                        RetrofitInstance.communicate({RetrofitInstance.api.getUsers(25, 0, filter = "Name:${ApplicationManager.searchApiWithRegex(actvAddresse.text.toString())},Role:1")}, {

                            val response = it as List<UserData>
                            if(actvAddresse.text.length >= 2) {


                                if(response.isNotEmpty()) {
                                    addresseAdapter.userList = response

                                    CoroutineScope(Dispatchers.Main).launch {
                                        addresseAdapter.notifyDataSetChanged()
                                        if(actvAddresse.isFocused) {
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

        tilAddresse.setBoxCornerRadii(radii, radii, radii, radii)
        recyclerView.isVisible = false

    }

    fun showList(){
        recyclerView.isVisible = true
        tilAddresse.setBoxCornerRadii(radii,radii,0f,0f)
    }

    fun inflate(){
        View.inflate(context, R.layout.text_input_with_chips, this)
    }


}