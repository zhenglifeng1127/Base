package com.android.base.application

import android.content.Context

/**

 * @Author zero
 * @Date 2021/4/1-16:44
 */
interface IModuleInit {
    fun onCreate() {}
    fun attachBaseContext(base: Context) {}
    fun onLowMemory() {}
    fun onTrimMemory(level: Int) {}
    fun onTerminate() {}
}