package com.android.base.utils


import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.android.base.config.AppManager
import com.android.base.config.AppManager.isExit
import com.android.base.config.L
import com.android.base.utils.other.RomUtils

import java.util.*
import kotlin.system.exitProcess

/**
 * activity拓展（不包含第三方库调用）
 */

/**
 * 获取自定义view布局简化
 *
 * @param id 布局xml id
 *
 */
fun Activity.inflate(@LayoutRes id: Int): View {
    return this.layoutInflater.inflate(id, null, false)
}

/**
 * 跳转页面
 *
 * @param cls 目标页面class
 *
 * @param bundle 附加数据，可为空
 *
 */
inline fun <reified T : Activity> Activity.openNext(bundle: Bundle? = null, isFinish: Boolean = false) {
    startActivity(Intent().apply {
        setClass(AppManager.endOfStack(), T::class.java)
        bundle?.let {
            putExtras(it)
        }
    })
    if (isFinish) {
        finish()
    }
}


/**
 * 带返回的页面跳转
 *
 * @param cls 目标页面class
 *
 * @param code 请求code
 *
 * @param bundle 附加数据,可为空
 *
 */
inline fun <reified T : Activity> Activity.forResult(code: Int, bundle: Bundle? = null) {
    startActivityForResult(Intent().apply {
        setClass(AppManager.endOfStack(), T::class.java)
        bundle?.let {
            putExtras(it)
        }
    }, code)

}

/**
 * 带返回的页面跳转返回
 *
 * @param code 请求code
 *
 * @param bundle 附加数据,可为空
 *
 */
fun Activity.resultBack(code: Int, bundle: Bundle?) {
    val intent = Intent()
    bundle?.let {
        intent.putExtras(bundle)
    }
    this.setResult(code, intent)
    this.finish()
}

/**
 * 双击退出
 */
fun Activity.exitApp() {
    val tExit: Timer
    if (!isExit) {
        isExit = true // 准备退出
        this.toast("再点击一次退出")
        tExit = Timer()
        tExit.schedule(object : TimerTask() {
            override fun run() {
                isExit = false // 取消退出
            }
        }, 2000) // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
    } else {
        try {
            AppManager.close()
            val activityMgr = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityMgr.killBackgroundProcesses(this.packageName)
            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

/**
 * 隐藏键盘
 */
fun Activity.hideInput() {
    val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    val v = window.peekDecorView()
    v?.let {
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}


/**
 * 点击View是否需要关闭软键盘
 */
fun View.isShouldHideKeyboard(event: MotionEvent): Boolean {
    if (this is EditText) {
        val l = intArrayOf(0, 0)
        this.getLocationInWindow(l)
        val left = l[0]
        val top = l[1]
        val bottom = top + this.getHeight()
        val right = left + this.getWidth()
        return !(event.x > left && event.x < right
                && event.y > top && event.y < bottom)
    }
    // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
    return false
}

/**
 * 根据机型选择打开应用设置
 */
fun Activity.openAppConfig(pageName: String) {
    if (RomUtils.isFlyme()) {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.putExtra("packageName", pageName)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return
    }
    if (RomUtils.isMiui()) {
        val i = Intent("miui.intent.action.APP_PERM_EDITOR")
        val componentName = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
        )
        i.component = componentName
        i.putExtra("extra_pkgname", pageName)
        try {
            startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return
    }
    if (RomUtils.isEmui()) {
        try {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val comp = ComponentName(
                    "com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity"
            )//华为权限管理
            intent.component = comp
            AppManager.endOfStack().startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return
    }

    val localIntent = Intent()
    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
    localIntent.data = Uri.fromParts("package", pageName, null)
    startActivity(localIntent)
}

fun Activity.checkPermissions(array: Array<String>,req:Int): Boolean {
    val permissions = array.copyOf().toMutableList()

    array.forEach {
        if (ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED) {
            permissions.remove(it)
        }else{
            L.i("permission",it)
        }
    }

    return if (permissions.size > 0) {
        ActivityCompat.requestPermissions(
                this,
                permissions.toTypedArray(),
                req)
        false
    } else {
        true
    }
}




