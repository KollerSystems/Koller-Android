package com.norbert.koller.shared.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.getAttributeColor
import com.norbert.koller.shared.setVisibilityBy

class SearchView : MaterialCardView {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    val editTextSearch : ExtraEditText
    private val buttonSearchCancel : Button

    init {

        View.inflate(context, R.layout.view_search, this)

        this.radius = 9999f

        editTextSearch = findViewById(R.id.editText_search)
        buttonSearchCancel = findViewById(R.id.button_search)

        editTextSearch.doOnTextChanged{_,_,_,_->
            buttonSearchCancel.setVisibilityBy(!editTextSearch.text.isNullOrEmpty())
        }

        buttonSearchCancel.setOnClickListener{
            editTextSearch.setText("")
        }



    }

}