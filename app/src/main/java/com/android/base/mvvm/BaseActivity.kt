package com.android.base.mvvm

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.android.base.R
import com.android.base.config.AppManager
import com.android.base.config.L
import com.android.base.utils.getResColor
import com.android.base.utils.hideInput
import com.android.base.utils.isShouldHideKeyboard
import com.android.base.utils.other.AndroidBug54971Workaround


import org.greenrobot.eventbus.EventBus

abstract class BaseActivity<T:ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        AppManager.add(this)

        binding = bindView()

        setContentView(binding.root)

        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),!isNullTranslucent())

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏


        initStatusBar(barColor())

        initArgs()

        initMap(savedInstanceState)

        initData()

        initListener()

    }



    open fun initMap(savedInstanceState: Bundle?) {

    }

    /**
     * 初始化配置
     */
    protected abstract fun initArgs()

    /**
     * 初始化数据调用
     */
    protected abstract fun initData()

    open fun barColor():Int = R.color.white

    open fun initListener() {

    }

    /**
     * 绑定DB布局
     */
    abstract fun bindView(): T

    open fun isNullTranslucent(): Boolean = false

    /**
     * 状态栏配置
     */
    private fun initStatusBar(@ColorRes id: Int) {

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R->{
                window.insetsController?.also {
                    it.hide(WindowInsets.Type.statusBars())
                    it.hide(WindowInsets.Type.navigationBars())
                }
                window.statusBarColor = getResColor(id)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.R -> { //5.0及以上
                val decorView: View = window.decorView
                val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                decorView.systemUiVisibility = option
                //根据上面设置是否对状态栏单独设置颜色
                window.statusBarColor = getResColor(id) //设置状态栏背景色
            }
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> { //4.4到5.0
//                val localLayoutParams: WindowManager.LayoutParams = window.attributes
//                localLayoutParams.flags =
//                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
//            }
            else -> {
                L.i("低于4.4的android系统版本不存在沉浸式状态栏")
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //android6.0以后可以对状态栏文字颜色和图标进行修改
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }



    /**
     * 销毁数据时调用
     */
    override fun onDestroy() {
        AppManager.remove(this)
        hideInput()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }

    /**
     * 黏贴返回处理
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {

        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            currentFocus?.let {
                if (it.isShouldHideKeyboard(ev)) {
                    hideInput()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }



}