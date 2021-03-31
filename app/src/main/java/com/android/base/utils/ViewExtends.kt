package com.android.base.utils

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.android.base.entity.CommonImage

fun View.visible(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

fun View.invisible(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.INVISIBLE
}

/**
 * 保留原有数据内存下刷新数据
 */
fun <T> MutableLiveData<MutableList<T>>.swap(data: MutableList<T>) {
    if (value == null) value = data
    else {
        value?.newInit(data)
    }
    postValue(value)
}

/**
 * 保留原有数据下添加
 */
fun <T> MutableLiveData<MutableList<T>>.add(data: MutableList<T>) {
    if (value == null) value = data
    else {
        value?.addAll(data)
    }
    postValue(value)
}


fun <T> MutableList<T>.newInit(data: MutableList<T>) {
    this.clear()
    this.addAll(data)
}


fun ArrayList<String>?.convert(max: Int): MutableList<CommonImage> {
    val list: MutableList<CommonImage> = ArrayList()
    if (this.isNullOrEmpty()) {
        list.add(CommonImage("", 0))
        return list
    }
    for (i in this.indices) {
        list.add(CommonImage(this[i], 1))
    }

    return if (this.size < max) {
        list.add(CommonImage("", 0))
        list
    } else {
        list
    }
}


fun MutableList<CommonImage>?.convert(isNet: Boolean = false): ArrayList<String> {
    val list: ArrayList<String> = ArrayList()
    if (this.isNullOrEmpty()) {
        return list
    }
    this.filter { it.type != 0 }.forEach { list.add(if(isNet)it.loadUrl.toString() else it.url.toString()) }
    return list
}


