package com.android.base.ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * 屏蔽刷新时其他控件点击，处理加载中时点击导致崩溃
 */
class ClickSwipeRefreshLayout : SwipeRefreshLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return if (isRefreshing)
            true
        else
            super.dispatchTouchEvent(ev)
    }
}