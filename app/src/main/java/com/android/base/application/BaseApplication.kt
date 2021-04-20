package com.android.base.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import com.alibaba.android.arouter.launcher.ARouter
import com.android.base.BuildConfig
import com.android.base.loadsir.NetCallback
import com.kingja.loadsir.core.LoadSir
import io.reactivex.rxjava3.plugins.RxJavaPlugins


/**
 * application 实际根据需求添加内容
 */
open class BaseApplication : Application() {

    init {
        mContext = this
    }


    companion object {
        lateinit var mContext: BaseApplication

        private val sp: SharedPreferences by lazy {
            mContext.applicationContext.getSharedPreferences(
                    mContext.packageName, Context.MODE_PRIVATE
            )
        }

        fun getData(): SharedPreferences {
            return sp
        }

    }


    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG_MODE){
            ARouter.openLog(); // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
        ModuleInitDelegate.reorder()
        ModuleInitDelegate.onCreate()


        RxJavaPlugins.setErrorHandler {
            //异常处理
        }

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        ModuleInitDelegate.attachBaseContext(base)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        ModuleInitDelegate.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        ModuleInitDelegate.onTrimMemory(level)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
        ModuleInitDelegate.onTerminate()
    }


    private fun initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(NetCallback())
                .commit()
    }

    /**
     * 调整比例处理修改系统字体大小导致适配问题
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        newConfig.fontScale = 1.0f
        super.onConfigurationChanged(newConfig)
    }



}