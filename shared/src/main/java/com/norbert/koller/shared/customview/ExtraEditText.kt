package com.norbert.koller.shared.customview

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText


class ExtraEditText : androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    var onKeyboardClose: (() -> Unit)? = null


    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            onKeyboardClose?.invoke()
            this.clearFocus()
        }
        return super.dispatchKeyEvent(event)
    }

}