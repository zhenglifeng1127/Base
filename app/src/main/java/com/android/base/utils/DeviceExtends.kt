package com.android.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.android.base.config.AppManager
import com.android.base.utils.other.Permission
import com.android.base.utils.other.PointUtils
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*

/**

 * @Author zero
 * @Date 2021/3/31-15:42
 */
class DeviceExtends {

    companion object {

        /**
         * 获取系统语言类型
         *
         * @return
         */
        fun getSystemLanguage(): String {
            return Locale.getDefault().language
        }

        /**
         * @return
         */
        fun getSystemLanguageList(): Array<Locale> {
            return Locale.getAvailableLocales()
        }

        /**
         * Android版本
         *
         * @return
         */
        fun getSystemVersion(): String {
            return Build.VERSION.RELEASE
        }

        /**
         * 机型版本参数
         *
         * @return
         */
        fun getSystemModel(): String {
            return Build.MODEL
        }

        /**
         * 系统定制商名称
         *
         * @return
         */
        fun getDeviceBrand(): String {
            return Build.BRAND
        }


        /**
         * 获取蓝牙地址
         * @return 蓝牙地址
         */
        fun getBluetoothAddress(): String? {
            try {
                val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val field = bluetoothAdapter.javaClass.getDeclaredField("mService")
                // 参数值为true，禁用访问控制检查
                field.isAccessible = true
                val bluetoothManagerService = field.get(bluetoothAdapter) ?: return null
                val method = bluetoothManagerService.javaClass.getMethod("getAddress")
                val address = method.invoke(bluetoothManagerService)
                return if (address != null && address is String) {
                    address
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        /*
       *用途:根据部分特征参数设备信息来判断是否为模拟器
       *返回:true 为模拟器
       */
        @SuppressLint("DefaultLocale")
        fun isFeatures(): Boolean {
            return (Build.FINGERPRINT.startsWith("generic")
                    || Build.FINGERPRINT.toLowerCase().contains("vbox")
                    || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                    || Build.MODEL.contains("google_sdk")
                    || Build.MODEL.contains("Emulator")
                    || Build.MODEL.contains("Android SDK built for x86")
                    || Build.MANUFACTURER.contains("Genymotion")
                    || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                    || "google_sdk" == Build.PRODUCT)
        }

        /*
*用途:根据CPU是否为电脑来判断是否为模拟器
*返回:true 为模拟器
*/
        fun checkIsNotRealPhone(): Boolean {
            val cpuInfo = readCpuInfo()
            return cpuInfo.contains("intel") || cpuInfo.contains("amd")
        }

        /*
         *用途:根据CPU是否为电脑来判断是否为模拟器(子方法)
         *返回:String
         */
        @SuppressLint("DefaultLocale")
        fun readCpuInfo(): String {
            var result = ""
            try {
                val args = arrayOf("/system/bin/cat", "/proc/cpuinfo")
                val cmd = ProcessBuilder(*args)

                val process = cmd.start()
                val sb = StringBuffer()
                var readLine: String? = ""
                val responseReader = BufferedReader(InputStreamReader(process.inputStream, "utf-8"))
                while (readLine != null) {
                    readLine = responseReader.readLine()
                    sb.append(readLine)
                }
                responseReader.close()
                result = sb.toString().toLowerCase()
            } catch (ex: Exception) {
            }

            return result
        }

        /*
     *用途:检测模拟器的特有文件
     *返回:true 为模拟器
     */
        private val known_pipes = arrayOf("/dev/socket/qemud", "/dev/qemu_pipe")

        fun checkPipes(): Boolean {
            for (i in known_pipes.indices) {
                val pipes = known_pipes[i]
                val qemuSocket = File(pipes)
                if (qemuSocket.exists()) {
                    Log.v("Result:", "Find pipes!")
                    return true
                }
            }
            Log.i("Result:", "Not Find pipes!")
            return false
        }

    }
}

/**
 * 快捷操作相机拍照
 * @param filePath 存储路径
 * @param req 请求码
 */
fun Activity.openCamera(filePath: String, req: Int) {
    if (checkPermissions(Permission.CAMERA, req)) {
        val imageUri = getFileUri(filePath) ?: return
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        grantUriPermissions(cameraIntent, imageUri)
        AppManager.endOfStack().startActivityForResult(cameraIntent, req)
    }
}

/**
 * 快捷操作相册
 * @param req 请求码
 */
fun Activity.openGallery(req: Int) {
    if (checkPermissions(Permission.STORAGE, req)) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        AppManager.endOfStack().startActivityForResult(intent, req)
    }
}

/**
 * 快捷裁剪
 * @param photoPath 图片路径
 * @param picPath 裁剪输出路径
 * @param width 输出宽
 * @param height 输出高
 * @param req 请求码
 */
fun Activity.pickPhoto(
    photoPath: String,
    picPath: String,
    width: Int,
    height: Int,
    req: Int
) {
    val imageUri = getFileUri(photoPath) ?: return
    val intent = Intent("com.android.camera.action.CROP")
    intent.setDataAndType(imageUri, "image/*")
    grantUriPermissions(intent, imageUri)
    // 设置裁剪
    intent.putExtra("crop", "true")
    // aspectX aspectY 是宽高的比例
    intent.putExtra("aspectX", 1)
    intent.putExtra("aspectY", 1)
    intent.putExtra("scale", true)
    // outputX outputY 是裁剪图片宽高
    intent.putExtra("outputX", width)
    intent.putExtra("outputY", height)
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
    intent.putExtra("noFaceDetection", true)
    intent.putExtra("return-data", false)
    //裁剪之后，保存在裁剪文件中，关键
    val imageCropUri = getFileUri(picPath) ?: return
    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCropUri)
    grantUriPermissions(intent, imageCropUri)
    AppManager.endOfStack().startActivityForResult(intent, req)
}

fun Activity.openTel(phone: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

/**
 * GPS是否开启
 * @param context 上下文
 * @return
 */
fun Context.isGpsEnable(): Boolean {
    val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    return gps || network
}



//打开地图
/**
 * 打开高德地图（公交出行，起点位置使用地图当前位置）
 *
 * t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
 *
 * @param dlat  终点纬度
 * @param dlon  终点经度
 * @param dname 终点名称
 */
fun Activity.openAMap(dlat: Double, dlon: Double, dName: String, appName: String) {
    if (isInstallApp("com.autonavi.minimap")) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.autonavi.minimap")
        intent.addCategory("android.intent.category.DEFAULT")
        intent.data = Uri.parse(
            "androidamap://route?sourceApplication=" + appName
                    + "&sname=我的位置&dlat=" + dlat
                    + "&dlon=" + dlon
                    + "&dname=" + dName
                    + "&dev=0&m=0&t=0"
        )
        startActivity(intent)
    } else {
        val data = PointUtils.gcj02_To_Bd09(dlat, dlon)
        openBaiduMap(data.wgLat, data.wgLon, dName)
    }
}

private fun openBaiduMap(dlat: Double, dlon: Double, dname: String) {
    if (AppManager.endOfStack().isInstallApp("com.baidu.BaiduMap")) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(
            ("baidumap://map/direction?origin=我的位置&destination=name:"
                    + dname
                    + "|latlng:" + dlat + "," + dlon
                    + "&mode=driving&sy=3&index=0&target=1")
        )
        AppManager.endOfStack().startActivity(intent)
    } else {
        val data = PointUtils.bd09_To_Gcj02(dlat, dlon)
        openTentcentMap(data.wgLat, data.wgLon, dname)
    }
}

/**
 * 调用腾讯地图
 *
 */
private fun openTentcentMap(dlat: Double, dlon: Double, dName: String) {
    if (AppManager.endOfStack().isInstallApp(
            "com.baidu.BaiduMap"
        )
    ) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.data = Uri.parse(
            ("qqmap://map/routeplan?" +
                    "type=drive" +
                    "&from=" +
                    "&fromcoord=" +
                    "&to=" + dName +
                    "&tocoord=" + dlat + "," + dlon +
                    "&policy=0" +
                    "&referer=appName")
        )
        AppManager.endOfStack().startActivity(intent)
    } else {
        openBrowserMap(dlat, dlon, dName)
    }
}


/**
 * 打开网页版 导航
 * 不填我的位置，则通过浏览器定未当前位置
 *
 */
private fun openBrowserMap(dlat: Double, dlon: Double, dname: String) {

    val intent = Intent()
    intent.action = "android.intent.action.VIEW"
    intent.data = Uri.parse(
        ("http://uri.amap.com/navigation?" +
                "from=&to=" + dlon + "," + dlat + "," + dname +
                "&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0")
    )
    AppManager.endOfStack().startActivity(intent)

}
