package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentBase
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentStatic
import com.norbert.koller.shared.recycleradapters.ListItem

class AttachView : ConstraintLayout {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    val card : MaterialCardView
    val textTip : TextView
    val image : ImageView
    val buttons : FlexboxLayout
    val buttonRemove : Button
    val buttonChange : Button

    init {

        View.inflate(context, R.layout.view_attach, this)

        card = findViewById(R.id.card)
        textTip = findViewById(R.id.text)
        image = findViewById(R.id.image)
        buttons = findViewById(R.id.fl_buttons)
        buttonRemove = findViewById(R.id.button_remove)
        buttonChange = findViewById(R.id.button_change)

        card.setOnClickListener{
            val fragmentManager = (context as AppCompatActivity)
            val dialog = ItemListDialogFragmentStatic(arrayListOf(
                ListItem("Fénykép készítése", null, AppCompatResources.getDrawable(context, R.drawable.camera), null, {

                }),
                ListItem("Fénykép kiválasztása", null, AppCompatResources.getDrawable(context, R.drawable.gallery), null, {

                })
            ))
            dialog.show(fragmentManager.supportFragmentManager, ItemListDialogFragmentBase.TAG)
        }
    }

}