package com.android.base.utils

import android.widget.ImageView
import com.android.base.utils.other.QrUtils

/**
 * 设置二维码
 */
fun ImageView.setQr(text: String, width: Int) {
    val w = context.getDimenPx(width)
    this.setImageBitmap(QrUtils.createQRImage(text, w, w))
}