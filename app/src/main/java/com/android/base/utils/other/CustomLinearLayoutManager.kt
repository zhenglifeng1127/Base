package com.android.base.utils.other

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager(con: Context) :LinearLayoutManager(con){

    var isScrollEnabled = true

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled &&super.canScrollVertically()
    }

}