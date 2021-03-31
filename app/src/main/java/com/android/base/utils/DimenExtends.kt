package com.android.base.utils

import android.content.Context
import androidx.annotation.DimenRes

/**
    获取一些尺寸参数以及尺寸转换
 * @Author zero
 * @Date 2021/3/31-15:37
 */

//----------尺寸转换----------

fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.px2dp(pxValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}

fun Context.px2sp(pxValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (pxValue / scale + 0.5f).toInt()
}

//----------屏幕尺寸----------

fun Context.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.getScreenHeight(): Int {
    return resources.displayMetrics.heightPixels
}

/**
 * 获取dimen,根据适配而定
 */
fun Context.getDimen(@DimenRes id: Int): Float {
    return this.resources.getDimension(id)
}

/**
 * 获取dimen转换为PX,根据适配而定
 */
fun Context.getDimenPx(@DimenRes id: Int): Int {
    return this.resources.getDimensionPixelSize(id)
}


/**
 * 屏幕分辨率
 *
 * @return 返回分辨率值
 */
fun Context.getDensity(): Float {
    return resources.displayMetrics.density
}

/**
 * 屏幕DPI
 *
 * @return
 */
fun Context.getDpi(): Int {
    return resources.displayMetrics.densityDpi
}

/**
 * 获取状态栏高度
 *
 * @return 状态栏高度
 */
fun Context.getStatusBarHeight(): Int {
    // 获得状态栏高度
    val resourceId =resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}