package com.android.base.mvvm

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.android.base.config.ActivityConfig
import com.android.base.config.AppManager
import com.android.base.utils.hideInput
import com.android.base.utils.isShouldHideKeyboard


import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<T: ViewBinding>: AppCompatActivity() {

    protected lateinit var binding: T

    protected lateinit var config: ActivityConfig


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.add(this)

        val type = javaClass.genericSuperclass

        if (type is ParameterizedType) {
            val cls = type.actualTypeArguments[0] as? Class<T>
            val method = cls?.getMethod("inflate", LayoutInflater::class.java)
            binding = method?.invoke(null, layoutInflater) as T
            setContentView(binding.root)
        }

        initOther()

        initConfig(savedInstanceState)

        initData()

        initListener()


    }

    /**
     * 初始化配置
     */
    protected abstract fun initConfig(bundle: Bundle?)

    /**
     * 如果有其他想在设置VIEW之后，initConfig前初始化的用此方法
     */
    protected open fun initOther(){
        config = setBuilder()
    }

    protected open fun setBuilder(): ActivityConfig = ActivityConfig.Builder(this).build()


    /**
     * 初始化数据调用
     */
    protected open fun initData(){

    }

    /**
     * 初始化点击监听等等
     */
    open fun initListener(){

    }

    /**
     * 黏贴返回处理
     */
    open fun copyBack(){

    }


    override fun onDestroy() {
        super.onDestroy()
        if (config.build.IS_NEED_EVENTBUS) {
            EventBus.getDefault().unregister(this)
        }
        AppManager.remove(this)
    }



//    /**
//     * 绑定DB布局
//     */
//    abstract fun bindView(): T


    /**
     * 配合处理粘贴板响应事件
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            copyBack()
        }
    }

    /**
     * 当目标失去焦点时直接隐藏键盘
     */
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