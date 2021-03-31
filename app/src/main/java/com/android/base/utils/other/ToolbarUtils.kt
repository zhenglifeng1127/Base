package com.android.base.utils.other

import android.graphics.Color
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import com.android.base.R
import com.android.base.config.AppManager
import com.android.base.utils.*
import com.google.android.material.appbar.AppBarLayout



import kotlin.math.abs

/**
 * toolbar设置类，如果用bing，请绑定title
 */
class ToolbarUtils(val build: Builder) {

    private var isSupport = false

    init {
        setHeight()
        setMenu()
        setNavigation()
        setColor()
        setTitle()
    }

    private fun setColor() {
        if (build.color != -1) {
            build.toolbar?.setBackgroundResource(build.color)
        }
    }


    fun setHeight() {
        build.toolbar?.translucent()
    }

    fun setMenu() {
        build.toolbar?.let {
            isSupport = it.setMenuAndClick(isSupport,  build.menuId, build.listener, build.isActivity)
        }
    }

    fun setNavigation() {
        build.toolbar?.let {
            isSupport = it.setNavigation(isSupport, build.icon, build.clickListener, build.isActivity)
        }
    }


    fun setTitle() {
        build.titleView?.let {
            build.toolbar?.title =""
            it.text = build.titleText
            it.setTextColor(AppManager.endOfStack().getResColor(build.titleColor))
            return
        }

        build.toolbar?.let {
            it.title = build.titleText
            it.titleMarginStart = it.context.getDimenPx(R.dimen.dp_10)
        }

    }


    /**
     * 非折叠式请勿调用,针对纯色使用
     *
     * @param colorRes color资源文件颜色ID
     */
    fun offsetColor(@ColorInt colorRes: Int): ToolbarUtils {
        build.toolbar?.let {
            (it.parent.parent as AppBarLayout).addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val fraction = abs(verticalOffset * 1.0f) / appBarLayout.totalScrollRange
                val color = changeAlpha(colorRes, fraction)
                it.setBackgroundColor(color)
                it.alpha = fraction
            })
        }
        return this
    }

//    /**
//     * 折叠式变更editText背景,折叠式在有自动滚动vp时滑动可能存在冲突
//     */
//    fun offsetColorTransport(@ColorInt colorRes: Int, et: EditText? = null): ToolbarUtils {
//        build.toolbar?.let {
//            (it.parent.parent as AppBarLayout).addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//                val fraction = abs(verticalOffset * 1.0f) / appBarLayout.totalScrollRange
//                val color = changeAlpha(colorRes, fraction)
//                it.setBackgroundColor(color)
//                et?.let { e ->
//                    if (fraction == 1f) {
//                        et.setBackgroundResource(R.drawable.shape_corners_dp15_line_blue)
//                    } else {
//                        et.setBackgroundResource(R.drawable.shape_corners_dp15_white)
//                    }
//                }
//            })
//        }
//        return this
//    }


    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

    class Builder {
        var icon = R.color.transport
        var listener: Toolbar.OnMenuItemClickListener? = null
        var menuId = -1
        var toolbar: Toolbar? = null
        var titleView:TextView? = null
        var isActivity = true
        var clickListener: View.OnClickListener? = View.OnClickListener { AppManager.finish() }
        var color: Int = -1
        var titleText:String = ""
        var titleColor:Int = R.color.tc1

        fun support(isActivity: Boolean): Builder {
            this.isActivity = isActivity
            return this
        }

        fun bar(toolbar: Toolbar): Builder {
            this.toolbar = toolbar
            toolbar.title = ""
            return this
        }

        fun title(title:String,color:Int = R.color.tc1):Builder{
            this.titleText = title
            this.titleColor = color
            return this
        }


        fun icon(@DrawableRes icon: Int, click: View.OnClickListener): Builder {
            this.icon = icon
            this.clickListener = click
            return this
        }

        fun menu(
            @MenuRes menuId: Int, listener: Toolbar.OnMenuItemClickListener?
        ): Builder {
            this.listener = listener
            this.menuId = menuId
            return this
        }

        fun color(@ColorRes color: Int): Builder {
            this.color = color
            return this
        }

        fun build(): ToolbarUtils {
            return ToolbarUtils(this)
        }


    }


}