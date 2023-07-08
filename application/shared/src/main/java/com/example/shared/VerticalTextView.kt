package com.example.shared

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView


class VerticalTextView : TextView {
    private var _width = 0
    private var _height = 0
    private val _bounds = Rect()

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?) : super(context) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // vise versa
        _height = measuredWidth
        _width = measuredHeight
        setMeasuredDimension(_width, _height)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.translate(_width.toFloat(), _height.toFloat())
        canvas.rotate(-90f)
        val paint = paint
        paint.color = textColors.defaultColor
        val text = text()
        paint.getTextBounds(text, 0, text.length, _bounds)
        canvas.drawText(
            text,
            compoundPaddingLeft.toFloat(),
            ((_bounds.height() - _width) / 2).toFloat(),
            paint
        )
        canvas.restore()
    }

    private fun text(): String {
        return super.getText().toString()
    }
}