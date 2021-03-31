package com.android.base.loadsir

import com.android.base.R
import com.kingja.loadsir.callback.Callback


/**
 * 网络出错替代页面。可以自行更改布局
 */
class NetCallback :Callback(){
    override fun onCreateView(): Int {
        return R.layout.item_callback_net
    }

}