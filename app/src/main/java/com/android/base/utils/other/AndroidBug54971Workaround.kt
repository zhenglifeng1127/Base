package com.android.base.utils.other

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

class AndroidBug54971Workaround//给View添加全局的布局监听器
private constructor(viewObserving: View, private var isTranslucent: Boolean) {

    private var mViewObserved: View = viewObserving//被监听的视图
    private var usableHeightPrevious: Int = 0//视图变化前的可用高度
    private var frameLayoutParams: ViewGroup.LayoutParams

    init {
        mViewObserved.viewTreeObserver
            .addOnGlobalLayoutListener { resetLayoutByUsableHeight(computeUsableHeight()) }
        frameLayoutParams = mViewObserved.layoutParams
    }

    companion object{
        /**
         * 关联要监听的视图
         *
         * @param viewObserving
         */
        fun assistActivity(viewObserving: View, isTranslucent: Boolean) {
            AndroidBug54971Workaround(viewObserving, isTranslucent)
        }

        /**
         * 判断底部是否有虚拟键
         * @param context
         * @return
         */
        @SuppressLint("PrivateApi")
        fun hasNavigationBar(context: Context): Boolean {
            var hasNavigationBar = false
            val rs = context.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    hasNavigationBar = true
                }
            } catch (e: Exception) {

            }
            return hasNavigationBar

        }
    }

    private fun resetLayoutByUsableHeight(usableHeightNow: Int) {
        //比较布局变化前后的View的可用高度
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将当前的View的可用高度设置成View的实际高度
            frameLayoutParams.height = usableHeightNow
            mViewObserved.requestLayout()//请求重新布局
            usableHeightPrevious = usableHeightNow
        }
    }
    /**
     * 计算视图可视高度
     *
     * @return
     */
    private fun computeUsableHeight(): Int {
        val r = Rect()
        mViewObserved.getWindowVisibleDisplayFrame(r)
        return if (isTranslucent) r.bottom else r.bottom - r.top
    }


}