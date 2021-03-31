package com.android.base.utils

import android.view.View
import android.widget.AbsListView
import com.android.base.R
import com.android.base.config.AppManager


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

/***
 * 带延迟过滤点击事件的 View 扩展
 * @param delay Long 延迟时间，默认500毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickDelay(delay: Long = 500, block: (T) -> Unit) {
    triggerDelay = delay
    setOnClickListener {
        if (clickEnable()) {
            block(this)
        }
    }
}


fun <T : View> T.clickRxDelay(p: Array<String>,pageName:String = context.packageName,delay: Long = 500, block: (T) -> Unit) {
    triggerDelay = delay
    setOnClickListener {
//        RxUtils.getPermission(p)?.let { its ->
//            its.subscribe {
//                L.i(
//                        "result",it.toString()
//                )
//
//                if (it) {
//                    if (clickEnable()) {
//                        block(this)
//                    }
//                } else {
//                    AppManager.endOfStack().dialog().normal(
//                            title = "温馨提示",content = "权限不足，是否跳转应用配置"
//                    ) {
//                        AppManager.endOfStack().openAppConfig(pageName)
//                    }
//                }
//            }
//        }
    }
}


/**
 * 简易弹出框点击
 */
fun <T : View> T.checkClick(title:String = "温馨提示",content:String,delay: Long = 500, block: (T) -> Unit){
    triggerDelay = delay
    setOnClickListener {
        if (clickEnable()) {
            AppManager.popup().normal(
                title = title,content = content
            ) {
                block(this)
            }
        }
    }
}

/**
 * 简易依附于View列表弹出
 */
fun <T : View> T.clickAttach(title:Array<String>,icon:IntArray? = null,delay: Long = 500, block: (T,Int,String) -> Unit){
    triggerDelay = delay
    setOnClickListener {
        if (clickEnable()) {
            AppManager.popup().attach(
                this, title, icon
            ) { p, text ->
                block(this,p,text)
            }
        }
    }
}



/**
 * 常规列表点击
 */
fun <T : AbsListView> T.clickListItemDelay(delay: Long = 500, block: (T, Int) -> Unit) {
    triggerDelay = delay
    setOnItemClickListener { _, _, position, _ ->
        if (clickEnable()) {
            block(this, position)
        }
    }
}

/**
 * 附加第三方应用安装判断判断点击事件
 */
fun <V : View> V.otherAppClick(packageName:String = context.packageName,name: String ="对应软件", block: (V) -> Unit) {
    clickDelay { v ->
        if(context.isInstallApp(packageName)){
            block(v)
        }else{
            AppManager.popup().normal(
                title = "温馨提示",content = "请先确认安装"+name+"后，再进行操作"
            ) {

            }
        }
    }
}

/**
 * 获取权限点击事件，具体权限适配根据版本调整，配套文件other-PERMISSION
 */
fun <V : View> V.rxClick(p: Array<String>,pageName:String = context.packageName, block: (V) -> Unit) {
    clickDelay { v ->

//        RxUtils.getPermission(p)?.let { its ->
//            its.subscribe {
//                L.i(
//                    "result",it.toString()
//                )
//                if (it) {
//                    block(v)
//                } else {
//                    AppManager.endOfStack().dialog().normal(
//                        title = "温馨提示",content = "权限不足，是否跳转应用配置"
//                    ) {
//                        AppManager.endOfStack().openAppConfig(pageName)
//                    }
//                }
//            }
//        }
    }
}

//fun <V:View> V.openCamera(path:String,pageName: String,req :Int = 666){
//    this.rxClick(Permission.CAMERA,pageName){
//        DeviceUtils.openCamera(AppManager.endOfStack(), Constants.GALLERY_PATH+path,req)
//    }
//}



///**
// * 根据坐标打开外部地图导航点击事件，支持高德，百度，网页等
// */
//fun <T:View>T.clickDelayMap(latLng: LatLng, name: String, delay: Long = 500) {
//    setOnClickListener {
//        if (clickEnable()) {
//            DeviceUtils.openAMap(latLng.latitude,latLng.longitude,name)
//        }
//    }
//}