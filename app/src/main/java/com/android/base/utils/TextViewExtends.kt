package com.android.base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.*
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.android.base.config.AppManager
import com.android.base.utils.other.NumberFilter
import java.util.*


/***
 *  TextView 及其子类拓展类工具
 */


enum class DrawableType {
    RIGHT,
    LEFT,
    TOP,
    BOTTOM
}

/**
 * 设置drawable，枚举为方向
 */
fun TextView.drawable(@DrawableRes id: Int, type: DrawableType, width: Int? = null) {
    val dra = ContextCompat.getDrawable(this.context, id)
    if (width == null)
        dra?.setBounds(0, 0, dra.minimumWidth, dra.minimumHeight)
    else
        dra?.setBounds(0, 0, width, width)
    when (type) {
        DrawableType.TOP -> this.setCompoundDrawables(
            compoundDrawables[0],
            dra,
            compoundDrawables[2],
            compoundDrawables[3]
        )
        DrawableType.LEFT -> this.setCompoundDrawables(
            dra,
            compoundDrawables[1],
            compoundDrawables[2],
            compoundDrawables[3]
        )
        DrawableType.RIGHT -> this.setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            dra,
            compoundDrawables[3]
        )
        DrawableType.BOTTOM -> this.setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            compoundDrawables[2],
            dra
        )
    }
}

fun TextView.drawable(
    left: Int = -1,
    right: Int = -1,
    top: Int = -1,
    bottom: Int = -1,
    width: Int? = null
) {

    this.setCompoundDrawables(
        if (left != -1)
            ContextCompat.getDrawable(this.context, left)?.apply {
                if (width == null)
                    setBounds(0, 0, minimumWidth, minimumHeight)
                else
                    setBounds(0, 0, width, width)
            }
        else null,
        if (top != -1)
            ContextCompat.getDrawable(this.context, top)?.apply {
                if (width == null)
                    setBounds(0, 0, minimumWidth, minimumHeight)
                else
                    setBounds(0, 0, width, width)
            }
        else null,
        if (right != -1)
            ContextCompat.getDrawable(this.context, right)?.apply {
                if (width == null)
                    setBounds(0, 0, minimumWidth, minimumHeight)
                else
                    setBounds(0, 0, width, width)
            }
        else null,
        if (bottom != -1)
            ContextCompat.getDrawable(this.context, bottom)?.apply {
                if (width == null)
                    setBounds(0, 0, minimumWidth, minimumHeight)
                else
                    setBounds(0, 0, width, width)
            }
        else null
    )
}


fun TextView.drawable(dra: Drawable, type: DrawableType, width: Int? = null) {
//    val dra = ContextCompat.getDrawable(this.context, id)
    if (width == null)
        dra.setBounds(0, 0, dra.minimumWidth, dra.minimumHeight)
    else
        dra.setBounds(0, 0, width, width)
    when (type) {
        DrawableType.TOP -> this.setCompoundDrawables(
            compoundDrawables[0],
            dra,
            compoundDrawables[2],
            compoundDrawables[3]
        )
        DrawableType.LEFT -> this.setCompoundDrawables(
            dra,
            compoundDrawables[1],
            compoundDrawables[2],
            compoundDrawables[3]
        )
        DrawableType.RIGHT -> this.setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            dra,
            compoundDrawables[3]
        )
        DrawableType.BOTTOM -> this.setCompoundDrawables(
            compoundDrawables[0],
            compoundDrawables[1],
            compoundDrawables[2],
            dra
        )
    }
}


/**
 * 清空drawable
 */
fun TextView.drawableNull() {
    setCompoundDrawables(
        null,
        null,
        null,
        null
    )
}

/**
 * 设置drawable型color
 */
fun TextView.setColorDrawable(@ColorRes id: Int) {
    if (Build.VERSION.SDK_INT > 22)
        this.setTextColor(resources.getColorStateList(id, null))
    else
        this.setTextColor(resources.getColorStateList(id))
}

/**
 * 设置文本颜色，通过id
 */
fun TextView.setResColor(@ColorRes id: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, id))
}

/**
 * 设置text文本过滤空指针类型
 */
fun TextView.setAnyText(any: Any?) {
    this.text = any?.toString() ?: ""
}

/**
 * 设置text文本大小变化
 */
fun TextView.textSizeSpan(text: String, start: Int, end: Int, size: Int) {
    val spannableString = SpannableString(text)
    val absoluteSizeSpan = AbsoluteSizeSpan(size, true)
    spannableString.setSpan(absoluteSizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}

/**
 * 设置text文本部分大小变化,支持组，size必须统一
 */
fun TextView.textSizeSpan(text: String, start: IntArray, end: IntArray, size: IntArray) {
    if (start.size == end.size && start.size == size.size) {
        val spannableString = SpannableString(text)
        for (i in start.indices) {
            val absoluteSizeSpan = AbsoluteSizeSpan(size[i], true)
            spannableString.setSpan(
                absoluteSizeSpan,
                start[i],
                end[i],
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
        this.text = spannableString
    }
}

/**
 * 设置text文本部分颜色变化
 */
fun TextView.textColorSpan(text: String, start: Int, end: Int, color: Int) {
    val spannableString = SpannableString(text)
    val span = ForegroundColorSpan(ContextCompat.getColor(this.context, color))
    spannableString.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}

fun TextView.clickSpan(start: Int, end: Int, color: Int, click: ClickableSpan) {
    val style = SpannableStringBuilder()

    //设置文字
    style.append(text);

    //设置部分文字点击事件
    style.setSpan(click, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    //设置部分文字颜色
    val foregroundColorSpan = ForegroundColorSpan(
        ContextCompat.getColor(
            AppManager.endOfStack(), color
        )
    )
    style.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    text = style
    this.movementMethod = LinkMovementMethod.getInstance()
}

/**
 * toString并去除空格
 */
fun TextView.tvString(): String {
    return if (this.text == null) "" else this.text.toString().trim()
}

/**
 * 获取文本double值，空与不符合规则取0.0
 */
fun TextView.tvDouble(): Double {
    val str: String = text.toString()
    if (str.isEmpty()) {
        return 0.0
    }
    if (!Regex("^(([1-9]\\d*)|0)(\\.\\d{1,3})?$").matches(str)) {
        return 0.0
    }
    return this.text.toString().trim().toDouble()
}

/**
 * 延迟弹出键盘
 */
fun EditText.focusInput(context: Context?) {
    requestFocus()
    val timer = Timer()
    timer.schedule(object : TimerTask() {
        override fun run() {
            if (context != null) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }, 1000)
}

/**
 * 显示或着隐藏文本
 */
fun EditText.hide(isChecked: Boolean) {
    this.transformationMethod =
        if (isChecked) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
    this.setSelection(this.length())
}

/**
 * 获取焦点，并打开键盘
 */
fun EditText.showInput() {
    requestFocus()
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * 禁止复制
 */
fun EditText.closeCopy() {
    isLongClickable = false

    customSelectionActionModeCallback = object : ActionMode.Callback {

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {

        }

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return false
        }
    }
    imeOptions = EditorInfo.IME_FLAG_NO_EXTRACT_UI
}

fun EditText.setNumMode() {
    val filters = arrayOf<InputFilter>(NumberFilter())
    this.filters = filters
}