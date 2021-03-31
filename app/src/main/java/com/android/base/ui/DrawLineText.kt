package com.android.base.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.android.base.R
import com.android.base.utils.getDimen
import com.android.base.utils.getResColor
import kotlin.math.abs

class DrawLineText : AppCompatTextView {

    private var direction = 0

    private var crop = 0f

    private var lineWidth = 0f

    private var left = 0f

    private var top = 0f

    private var right  = 0f

    private var bottom  = 0f

    private var isDash = false

    private var cropStatus = 0


    private val linePaint by lazy {
        if (isDash) {
            Paint().apply {
                flags = Paint.ANTI_ALIAS_FLAG
                color = context.getResColor(R.color.tcHint)
                pathEffect = DashPathEffect(floatArrayOf(5f, 2f), 0f)
                style = Paint.Style.STROKE
                strokeWidth = context.getDimen(R.dimen.dp_1)
            }
        } else {
            Paint().apply {
                flags = Paint.ANTI_ALIAS_FLAG
                color = context.getResColor(R.color.tcHint)
                style = Paint.Style.STROKE
                strokeWidth = context.getDimen(R.dimen.dp_1)
            }
        }

    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawLineText)
        direction = mTypedArray.getInteger(R.styleable.DrawLineText_lineDirection, direction)
        cropStatus = mTypedArray.getInteger(R.styleable.DrawLineText_drawCropDirection, cropStatus)
        lineWidth = mTypedArray.getDimension(R.styleable.DrawLineText_drawWidth, lineWidth)
        crop = mTypedArray.getDimension(R.styleable.DrawLineText_drawCrop, crop)
        isDash = mTypedArray.getBoolean(R.styleable.DrawLineText_isDash, isDash)
        mTypedArray.recycle()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        right = measuredWidth.toFloat()
        bottom = measuredHeight.toFloat()
        when (direction) {
            0 -> {
                //left
                countCrop(abs(top - bottom))
                canvas.drawLine(
                    left,
                    if (cropStatus != 2) top + crop else top,
                    left,
                    if (cropStatus != 1) bottom - crop else bottom,
                    linePaint
                )
            }
            1 -> {
                //right
                countCrop(abs(top - bottom))
                canvas.drawLine(
                    right,
                    if (cropStatus != 2) top + crop else top,
                    right,
                    if (cropStatus != 1) bottom - crop else bottom,
                    linePaint
                )
            }
            2 -> {
                //top
                countCrop(abs(right - left))
                canvas.drawLine(
                    if (cropStatus != 2) left + crop else left,
                    top,
                    if (cropStatus != 1) right - crop else right,
                    top,
                    linePaint
                )
            }
            3 -> {
                countCrop(abs(right - left))
                canvas.drawLine(
                    if (cropStatus != 2) left + crop else left,
                    bottom,
                    if (cropStatus != 1) right - crop else right,
                    bottom,
                    linePaint
                )
            }
        }
    }

    fun setDirection(d:Int){
        direction = d
        invalidate()
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        invalidate()
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    private fun countCrop(line: Float) {
        if (lineWidth != 0f && lineWidth < line && crop == 0f) {
            crop = (line - lineWidth) / 2
        }
    }



}