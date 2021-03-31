package com.android.base.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore

/**

 * @Author zero
 * @Date 2021/3/31-15:39
 */

/**
 * uri动态读写权限授予
 *
 * @param intent
 * @param uri
 */
@SuppressLint("QueryPermissionsNeeded")
fun Context.grantUriPermissions(intent: Intent, uri: Uri) {
    val resolveInfoList = packageManager.queryIntentActivities(
        intent,
        PackageManager.MATCH_DEFAULT_ONLY
    )
    for (resolveInfo in resolveInfoList) {
        val packageName = resolveInfo.activityInfo.packageName
        grantUriPermission(
            packageName,
            uri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }
}


fun Context.getUriPath(data: Intent):String?{
    var imagePath: String? = null
    val uri = data.data
    if (DocumentsContract.isDocumentUri(this, uri)) {
        // 如果是document类型的Uri，则通过document id处理
        val docId = DocumentsContract.getDocumentId(uri)
        if ("com.android.providers.media.documents" == uri!!.authority) {
            val id = docId.split(":").toTypedArray()[1] // 解析出数字格式的id
            val selection = MediaStore.Images.Media._ID + "=" + id
            imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
        } else if ("com.android.providers.downloads.documents" == uri.authority) {
            val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
            imagePath = getImagePath(contentUri, null)
        }
    } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
        // 如果是content类型的Uri，则使用普通方式处理
        imagePath = getImagePath(uri, null)
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        // 如果是file类型的Uri，直接获取图片路径即可
        imagePath = uri.path
    }
    return imagePath
}

/**
 * 查询图库中是否存在有指定路径的图片
 * @param uri:路径URI
 * @param selection:筛选条件
 * @param context
 * @return
 */
private fun Context.getImagePath(uri: Uri?, selection: String?): String? {
    var path: String? = null
    // 通过Uri和selection来获取真实的图片路径
    val cursor: Cursor? = contentResolver?.query(uri!!, null, selection, null, null)
    if (cursor != null) {

        var i = 0
        while (i < cursor.columnCount) {
            var ss = cursor.getColumnName(i)
            i++
        }
        if (cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        }
        cursor.close()
    }
    return path
}