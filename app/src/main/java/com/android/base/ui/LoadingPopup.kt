package com.android.base.ui

import android.content.Context
import com.android.base.R
import com.android.base.config.AppManager
import com.lxj.xpopup.core.CenterPopupView

/**
 * 加载用loading可以自行调整样式
 */
class LoadingPopup(context: Context = AppManager.endOfStack()) :CenterPopupView(context){

    override fun getImplLayoutId(): Int {
        return R.layout.item_popup_loading
    }
}