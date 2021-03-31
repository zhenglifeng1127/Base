package com.android.base.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.android.base.R
import com.android.base.utils.getDimen
import com.android.base.utils.getResColor

class CornerText : AppCompatTextView {

    private var left = 0f

    private var top = 0f

    private var right = 0f

    private var bottom = 0f

    private var radius = context.getDimen(R.dimen.dp_9)

    private var bgColor = context.getResColor(R.color.mainColor)

    private var bgColorEnd = context.getResColor(R.color.mainColor50)

    private var paintStyle = Paint.Style.FILL

    private var isGradient = false

    private val linePaint by lazy {
        Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            color = bgColor
            style = paintStyle
            strokeWidth = context.getDimen(R.dimen.dp_1)
        }
    }

    private val paint by lazy {
        Paint().apply {
            strokeWidth = context.getDimen(R.dimen.dp_1)
            style = Paint.Style.FILL_AND_STROKE
            shader = LinearGradient(
                left,
                top,
                right,
                bottom,
                bgColor,
                bgColorEnd,
                Shader.TileMode.MIRROR
            )
            isAntiAlias = true
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
        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerText)
        radius = mTypedArray.getDimension(R.styleable.CornerText_cornerTextRadius, radius)
        bgColor = mTypedArray.getColor(R.styleable.CornerText_cornerTextBgColor, bgColor)
        bgColorEnd = mTypedArray.getColor(R.styleable.CornerText_cornerTextBgColorEnd, bgColorEnd)
        paintStyle = when (mTypedArray.getInt(R.styleable.CornerText_bgDrawStyle, 0)) {
            1 -> {
                isGradient = false
                Paint.Style.STROKE
            }
            2 -> {
                isGradient = true
                Paint.Style.FILL
            }
            else -> {
                isGradient = false
                Paint.Style.FILL
            }
        }

        mTypedArray.recycle()
    }


    fun setBgColor(color1: Int) {
        bgColor = context.getResColor(color1)
        right = measuredWidth.toFloat()
        bottom = measuredHeight.toFloat()
        linePaint.apply {
            color = bgColor
        }
        invalidate()
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val rectF = RectF()
        right = measuredWidth.toFloat()
        bottom = measuredHeight.toFloat()
        if (paint.style == Paint.Style.STROKE) {
            rectF.left = context.getDimen(R.dimen.dp_1).div(2)
            rectF.top = context.getDimen(R.dimen.dp_1).div(2)
            rectF.right = right - context.getDimen(R.dimen.dp_1).div(2)
            rectF.bottom = bottom - context.getDimen(R.dimen.dp_1).div(2)
        } else {
            rectF.left = left
            rectF.top = top
            rectF.right = right
            rectF.bottom = bottom
        }
        canvas.drawRoundRect(rectF, radius, radius, if (isGradient) paint else linePaint)

//        canvas.drawRadio(left,right,top,bottom,radius,if(isGradient) paint else linePaint)
        super.onDraw(canvas)
    }

}