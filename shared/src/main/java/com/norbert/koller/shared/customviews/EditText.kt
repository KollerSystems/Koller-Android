package com.norbert.koller.shared.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent


class EditText : androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    var onKeyboardClose: (() -> Unit)? = null


    @SuppressLint("GestureBackNavigation")
    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            onKeyboardClose?.invoke()
            this.clearFocus()
        }
        return super.dispatchKeyEvent(event)
    }

}