package com.android.base.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.android.base.R

class XEditText : AppCompatEditText, View.OnFocusChangeListener, TextWatcher {


    private var hasFocus: Boolean = false
    private var mClearDrawable: Drawable?

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, android.R.attr.editTextStyle)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    init {
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_edit_delete)
        }
        mClearDrawable?.setBounds(0, 0, 32, 32)
        //默认设置隐藏图标
        setClearIconVisible(false)
        //设置焦点改变的监听
        onFocusChangeListener = this
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }



    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1], right, compoundDrawables[3]
        )
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {

                val touchable =
                    event.x > width - totalPaddingRight && event.x < width - paddingRight

                if (touchable) {
                    this.setText("")
                }
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (hasFocus) {
            setClearIconVisible(text.isNotEmpty())
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFocus = hasFocus
        text?.let {
            if (hasFocus) {
                setClearIconVisible(it.isNotEmpty())
            } else {
                setClearIconVisible(false)
            }
        }

    }

    /**
     * 设置晃动动画
     */
    fun setShakeAnimation() {
        this.animation = shakeAnimation(5)
    }


    /**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     * @return
     */
    private fun shakeAnimation(counts: Int): Animation {
        val translateAnimation = TranslateAnimation(0f, 10f, 0f, 0f)
        translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
        translateAnimation.duration = 1000
        return translateAnimation
    }
}