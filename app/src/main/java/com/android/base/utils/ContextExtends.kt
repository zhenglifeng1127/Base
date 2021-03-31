package com.android.base.utils


import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import kotlin.collections.ArrayList

/**
 * 显示toast
 */
fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

/**
 * 显示toast，根据资源文件
 */
fun Context.toast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

/**
 * 显示toast，居中
 */
fun Context.centerToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    val t = Toast.makeText(this, resId, duration)
    t.setGravity(Gravity.CENTER, 0, 0)
    t.show()
}


//----------NetWork----------

/**
 * 打开网络设置
 */
fun Context.openWirelessSettings() {
    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}


/**
 * 退回到桌面
 */
fun Context.startHomeActivity() {
    val homeIntent = Intent(Intent.ACTION_MAIN)
    homeIntent.addCategory(Intent.CATEGORY_HOME)
    startActivity(homeIntent)
}

/**
 * 网络检测
 */
fun Context.getActiveNetworkInfo(): Boolean {
    val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        manager?.run {
            getNetworkCapabilities(this.activeNetwork)?.run {
                return when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            }
        }
    } else {
        manager?.run {
            activeNetworkInfo?.run {
                if (type == ConnectivityManager.TYPE_WIFI) {
                    return true
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    return true
                }
            }
        }
    }
    return false
}

/**
 * 获取arrays转换成list
 */
fun Context.listOf(@ArrayRes id: Int): ArrayList<String> {
    return this.resources.getStringArray(id).toList() as ArrayList<String>
}

/**
 * 获取arrays转换成string[]
 */
fun Context.stringArray(@ArrayRes id: Int): Array<String> {
    return this.resources.getStringArray(id)
}

/**
 * 简易Inflate
 */
fun Context.inflate(@LayoutRes id: Int): View {
    return LayoutInflater.from(this)
        .inflate(id, null, false)
}


/**
 * 获取资源颜色
 */
fun Context.getResColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}


/**
 * 跳转拨号页面
 */
fun Context.callPhone(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

//------------------app utils--------------------

/**
 * 检查手机上是否安装了指定的软件
 *
 * @param name：应用包名
 */
@Synchronized
fun Context.isInstallApp(name: String): Boolean {
    val manager: PackageManager = packageManager
    val infoList: MutableList<PackageInfo> = manager.getInstalledPackages(0)

    for (info: PackageInfo in infoList) {
        if (name == info.packageName) {
            return true
        }
    }
    return false
}

/**
 * 是否安装支付宝
 */
fun Context.isAliPayInstalled(): Boolean {
    val uri = Uri.parse("alipays://platformapi/startApp")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    val componentName = intent.resolveActivity(packageManager)
    return componentName != null
}

/***
 * 获取app versionName
 */
fun Context.getAppVersionName(): String? {
    try {
        val packageManager = packageManager
        val packageInfo = packageManager.getPackageInfo(
            packageName, 0
        )

        return packageInfo.versionName
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}

/***
 * 获取app versionCode
 */
fun Context.getAppVersionCode(): Long {
    try {
        val packageManager = packageManager
        val packageInfo = packageManager.getPackageInfo(
            packageName, 0
        )
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        }else{
            packageInfo.versionCode.toLong()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return -1
}

//------------------file utils--------------------
/**
 * 根据file获取真实uri 兼容android版本
 */
fun Context.getFileUri(mFile: File): Uri? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(
            this,
            this.packageName + ".selector.provider",
            mFile
        )
    } else {
        Uri.fromFile(mFile)
    }
}

/**
 * 根据filePath获取真实uri 兼容android版本
 */
fun Context.getFileUri(path: String): Uri? {
    if (path.isEmpty()) {
        return null
    }
    val file = File(path)
    file.parentFile?.mkdirs()
    return getFileUri(file)
}




//------------------device utils--------------------