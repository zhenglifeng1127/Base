package com.android.base.utils.other

import android.webkit.JavascriptInterface
import com.android.base.entity.WebClickEvent
import org.greenrobot.eventbus.EventBus



class CommonJs {


    /**
     * 点击事件 发送event
     */
    @JavascriptInterface
    fun<T> clickWeb(type:Int,extra:T?) {
        EventBus.getDefault().post(WebClickEvent(type, extra))
    }

}