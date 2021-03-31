package com.android.base.utils

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.base.utils.other.CustomLinearLayoutManager


/**
 * recyclerView常用方法拓展
 */

private fun manager(con: Context, orientation: Int, isScroll: Boolean = true): LinearLayoutManager {

    return when (orientation) {
        RecyclerView.HORIZONTAL -> {
            object : LinearLayoutManager(con, orientation, false) {
                override fun canScrollHorizontally(): Boolean {
                    return isScroll
                }
            }
        }
        else -> {
            object : LinearLayoutManager(con, orientation, false) {
                override fun canScrollHorizontally(): Boolean {
                    return isScroll
                }
            }
        }
    }
}

private fun managerHeight(con: Context, orientation: Int): LinearLayoutManager {
    val manager = object : LinearLayoutManager(con) {
        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
            return RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
    manager.orientation = orientation
    return manager
}

/**
 * 管理外嵌滑动
 */
fun RecyclerView.shouldRefresh(swipe: SwipeRefreshLayout) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val topRowVerticalPosition =
                if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(
                    0
                ).top
            if (!swipe.isRefreshing)
                swipe.isEnabled = topRowVerticalPosition >= 0
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }
    })
}


/**
 * 管理是否可滑动
 */
fun RecyclerView.rvScroll(orientation: Int) {
    if (layoutManager == null) {
        val manager = CustomLinearLayoutManager(context)
        manager.orientation = orientation
        this.layoutManager = manager
    }
}


/**
 * 常规线性使用
 */
fun RecyclerView.rvArgs(
    orientation: Int,
    decoration: RecyclerView.ItemDecoration? = null,
    isScroll: Boolean = true
) {
    if (layoutManager == null) {
        this.layoutManager =
            manager(this.context, orientation, isScroll)
        decoration?.let {
            if (this.itemDecorationCount > 0) {
                this.removeItemDecorationAt(0)
            }
            this.addItemDecoration(decoration)
        }
    }
}


/**
 * 处理嵌套rv
 */
fun RecyclerView.rvArgsHeight(orientation: Int) {
    if (layoutManager == null)
        this.layoutManager =
            managerHeight(this.context, orientation)
}

fun RecyclerView.findLastVisible(position: Int): Boolean {
    if (this.layoutManager != null) {
        val p = (this.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        return if (p != -1) {
            position <= p
        } else {
            false
        }
    }
    return false
}


/**
 * 多行不同Item时使用，设置adapter后设置
 */
fun RecyclerView.rvGrid(
    size: Int,
    spanSizeLookup: GridLayoutManager.SpanSizeLookup,
    decoration: RecyclerView.ItemDecoration? = null
) {
    if (layoutManager == null) {
        val manager = GridLayoutManager(this.context, size)
        manager.spanSizeLookup = spanSizeLookup
        this.layoutManager = manager
        decoration?.let {
            if (itemDecorationCount > 0) {
                removeItemDecorationAt(0)
            }
            addItemDecoration(decoration)
        }
    }
}

/**
 * 常规网格布局使用
 */
fun RecyclerView.rvGrid(size: Int, decoration: RecyclerView.ItemDecoration? = null) {
    if (layoutManager == null) {
        this.layoutManager = GridLayoutManager(this.context, size)
        decoration?.let {
            if (itemDecorationCount > 0) {
                removeItemDecorationAt(0)
            }
            addItemDecoration(decoration)
        }
    }
}

fun RecyclerView.setAndRefresh(apt:RecyclerView.Adapter<*>){
    apply {
        if(adapter == null){
            adapter = apt
        }else{
            adapter?.notifyDataSetChanged()
        }
    }
}



