package com.android.base.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.android.base.R
import com.chad.library.adapter.base.BaseQuickAdapter


/**
 * baseQuickAdapter库点击事件拓展
 */


/**
 * get set
 * 给view添加一个上次触发时间的属性（用来屏蔽连击操作）
 */
private var <T : View>T.triggerLastTime: Long
    get() = if (getTag(R.id.triggerLastTimeKey) != null) getTag(R.id.triggerLastTimeKey) as Long else 0
    set(value) {
        setTag(R.id.triggerLastTimeKey, value)
    }

/**
 * get set
 * 给view添加一个延迟的属性（用来屏蔽连击操作）
 */
private var <T : View> T.triggerDelay: Long
    get() = if (getTag(R.id.triggerDelayKey) != null) getTag(R.id.triggerDelayKey) as Long else -1
    set(value) {
        setTag(R.id.triggerDelayKey, value)
    }

/**
 * 判断时间是否满足再次点击的要求（控制点击）
 */
private fun <T : View> T.clickEnable(): Boolean {
    var clickable = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        clickable = true
    }
    triggerLastTime = currentClickTime
    return clickable
}

/**
 * 延迟点击,针对baseQuickAdapter列表点击事件封装
 */
fun <D :Any?> RecyclerView.clickItemDelay(delay: Long = 500, block: (BaseQuickAdapter<D, *>, Int) -> Unit) {
    triggerDelay = delay
    adapter?.let {
        (it as BaseQuickAdapter<*, *>?)?.apply {
            setOnItemClickListener { _, _, position ->
                if (clickEnable()) {
                    block(it as BaseQuickAdapter<D, *>, position)
                }
            }
        }
    }
}

/**
 * 延迟点击,针对baseQuickAdapter子项点击事件封装
 */

fun <D :Any?> RecyclerView.clickChildItemDelay(@IdRes viewIds: IntArray, delay: Long = 500, block: (BaseQuickAdapter<D, *>, Int, Int) -> Unit) {
    triggerDelay = delay
    adapter?.let {
        (it as BaseQuickAdapter<*, *>?)?.apply {
            addChildClickViewIds(*viewIds)
            setOnItemChildClickListener { _, v, position ->
                if (clickEnable()) {
                    block(it as BaseQuickAdapter<D, *>, v.id, position)
                }
            }
        }
    }
}

/**
 * 延迟点击,针对baseQuickAdapter子项点击事件封装
 */
fun <D :Any?> RecyclerView.clickChildItemDelay(@IdRes viewIds: IntArray,delay: Long = 500, block: (BaseQuickAdapter<D, *>, View, Int,Int) -> Unit) {
    triggerDelay = delay
    adapter?.let {
        (it as BaseQuickAdapter<*, *>?)?.apply {
            addChildClickViewIds(*viewIds)
            setOnItemChildClickListener { _, v, position ->
                if (clickEnable()) {
                    block(it as BaseQuickAdapter<D, *>, v, position,v.id)
                }
            }
        }
    }
}

/**
 * 延迟点击,针对baseQuickAdapter子项点击事件封装
 */
fun <D :Any?> RecyclerView.clickChildItemDelay(@IdRes viewIds: IntArray,delay: Long = 500, block: (D, View) -> Unit) {
    triggerDelay = delay
    adapter?.let {
        (it as BaseQuickAdapter<*, *>?)?.apply {
            addChildClickViewIds(*viewIds)
            setOnItemChildClickListener { _, v, position ->
                if (clickEnable()) {
                    block((it as BaseQuickAdapter<D, *>).data[position], v)
                }
            }
        }
    }
}
