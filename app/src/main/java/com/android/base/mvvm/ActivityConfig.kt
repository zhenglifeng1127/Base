package com.android.base.mvvm

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.ColorRes
import com.android.base.R
import com.android.base.loadsir.NetCallback
import com.android.base.utils.getActiveNetworkInfo
import com.android.base.utils.getResColor
import com.android.base.utils.other.AndroidBug54971Workaround
import com.kingja.loadsir.core.LoadSir
import org.greenrobot.eventbus.EventBus

class ActivityConfig(val build:Builder) {


    init {
        initStatusBar(build.translucentColor,build.context)

        AndroidBug54971Workaround.assistActivity(build.context.findViewById(android.R.id.content),!build.IS_NEED_TRANSLUCENT)

        build.context.requestedOrientation = build.SCREEN_ORIENTATION//竖屏

        if(build.IS_NEED_SCREEN_CAPTURE){
            build.context.window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
        if(build.IS_NEED_EVENTBUS){
            EventBus.getDefault().register(build.context)
        }
    }

    class Builder(val context: Activity){
        //有需要沉浸式
        var IS_NEED_TRANSLUCENT = false
        //沉浸式状态栏默认颜色
        var translucentColor = R.color.transport
        //是否允许页面截屏
        var IS_NEED_SCREEN_CAPTURE = true
        //默认屏幕方向
        var SCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //是否检测网络
        var IS_NET_CHECK = true
        //是否需要注册eventBus
        var IS_NEED_EVENTBUS = false


        fun build():ActivityConfig{
            return ActivityConfig(this)
        }

    }


    /**
     * 状态栏配置
     */
    private fun initStatusBar(@ColorRes id: Int,context: Activity) {

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R->{
                context.window.insetsController?.also {
                    it.hide(WindowInsets.Type.statusBars())
                    it.hide(WindowInsets.Type.navigationBars())
                }
                context.window.statusBarColor = context.getResColor(id)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.R -> { //5.0及以上
                val decorView: View = context.window.decorView
                val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                decorView.systemUiVisibility = option
                //根据上面设置是否对状态栏单独设置颜色
                context.window.statusBarColor = context.getResColor(id) //设置状态栏背景色
            }
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> { //4.4到5.0
//                val localLayoutParams: WindowManager.LayoutParams = window.attributes
//                localLayoutParams.flags =
//                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
//            }
            else -> {
                Log.i("config","低于4.4的android系统版本不存在沉浸式状态栏")
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //android6.0以后可以对状态栏文字颜色和图标进行修改
            context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}