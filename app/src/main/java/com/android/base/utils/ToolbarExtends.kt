package com.android.base.utils

import android.os.Build
import android.text.TextPaint
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.base.config.AppManager


/**
 * 适配沉浸式状态栏高度
 */
fun Toolbar.translucent() {
    val h = context.getStatusBarHeight()
    val lp = this.layoutParams
    lp.height = lp.height + h
    this.layoutParams = lp
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        this.setPadding(0, h, 0, 0)
    }

}

fun Toolbar.centerTitle() {
    val width = context.getScreenWidth()
    val paint = TextPaint()
    val textWidth = paint.measureText(title.toString());
    val f = (width - textWidth) / 2
    this.titleMarginStart = f.toInt()
}


/**
 * Toolbar菜单栏点击，不影响前后顺序导致事件无效
 */
fun Toolbar.setMenuAndClick(
    isSupport: Boolean, @MenuRes menuId: Int,
    listener: Toolbar.OnMenuItemClickListener?,
    isOpen: Boolean
): Boolean {
    var tag = isSupport

    if (menuId != -1)
        this.inflateMenu(menuId)
    if (!tag)
        if (isOpen && AppManager.size() > 0) {
            (AppManager.endOfStack() as AppCompatActivity).setSupportActionBar(this)
            tag = true
        }
    this.setOnMenuItemClickListener(listener)
    return tag
}

/**
 * 左侧按钮及其事件
 */
fun Toolbar.setNavigation(
    isSupport: Boolean, @DrawableRes icon: Int,
    click: View.OnClickListener?,
    isOpen: Boolean
) : Boolean{
    var tag = isSupport
    if (icon != -1)
        this.setNavigationIcon(icon)
    if (!tag)
        if (isOpen && AppManager.size() > 0) {
            (AppManager.endOfStack() as AppCompatActivity).setSupportActionBar(this)
            tag = true
        }
    this.setNavigationOnClickListener(click)
    val actionBar: ActionBar? =  (AppManager.endOfStack() as AppCompatActivity).supportActionBar
    actionBar?.setDisplayShowTitleEnabled(false);
    return tag
}
