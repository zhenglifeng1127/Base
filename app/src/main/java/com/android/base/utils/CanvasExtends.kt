package com.android.base.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF

/**
 * 简易弧线拓展
 * @param x 左上角顶点坐标
 * @param y 左上角顶点坐标
 * @param radius 半径
 * @param startAngle 起始角度 上-90 下90 左180 右 0
 * @param sweepAngle 旋转角度
 *
 */
fun Path.arcToRadius(
        x: Float,
        y: Float,
        radius: Float,
        startAngle: Float,
        sweepAngle: Float,
        forceMoveTo: Boolean = false
) {
    arcTo(RectF().apply {
        left = x
        top = y
        right = x + radius * 2
        bottom = y + radius * 2
    }, startAngle, sweepAngle, forceMoveTo)
}

fun Canvas.drawCoupon(
        paint: Paint,
        line: Paint,
        tag: Paint,
        left: Float,
        right: Float,
        top: Float,
        bottom: Float,
        lineHeightTop: Float,
        widthTag: Float,
        heightTag: Float,
        initRadius: Float,
        cropRadius: Float,
        hasTag: Boolean = false
) {
    drawPath(Path().apply {
        moveTo(left + initRadius, top)
        arcToRadius(left, top, initRadius, -90f, -90f)
        lineTo(left, top + initRadius + lineHeightTop)
        arcToRadius(
                left - cropRadius,
                top + cropRadius + lineHeightTop,
                cropRadius,
                -90f,
                180f
        )
        lineTo(left, bottom - initRadius)
        arcToRadius(
                left,
                bottom - 2 * initRadius,
                initRadius,
                180f,
                -90f
        )
        lineTo(right - initRadius, bottom)
        arcToRadius(
                right - 2 * initRadius,
                bottom - 2 * initRadius,
                initRadius,
                90f,
                -90f
        )
        lineTo(right, top + 3 * initRadius + lineHeightTop)
        arcToRadius(
                right - cropRadius,
                top + cropRadius + lineHeightTop,
                cropRadius,
                90f,
                180f
        )
        lineTo(right, top + initRadius)
        arcToRadius(
                right - 2 * initRadius,
                top,
                initRadius,
                0f,
                -90f
        )
        close()
    }, paint)
    drawPath(Path().apply {
        val y = top + lineHeightTop + 2* cropRadius
        moveTo(left + initRadius + cropRadius, y)
        lineTo(right - left - initRadius - cropRadius, y)
    }, line)

    if (hasTag) {
        drawPath(Path().apply {
            moveTo(left + initRadius, top)
            arcToRadius(left, top, initRadius, -90f, -90f)
            lineTo(left, top + initRadius + heightTag)
            lineTo(left + widthTag, top + initRadius + heightTag)
            arcToRadius(
                    left + widthTag - initRadius,
                    top - initRadius + heightTag,
                    initRadius,
                    90f,
                    -90f
            )
            lineTo(left + widthTag + initRadius, top)
        }, tag)
    }
}

/**
 * 绘制闭合圆角背景
 */
fun Canvas.drawRadio(
        left: Float,
        right: Float,
        top: Float,
        bottom: Float,
        radius: Float,
        paint: Paint
) {
    drawPath(Path().apply {
        moveTo(left + radius, top)
        arcToRadius(
                left,
                top,
                radius,
                -90f,
                -90f
        )
        lineTo(left, bottom - radius)

        arcToRadius(
                left,
                bottom - 2 * radius,
                radius,
                180f,
                -90f
        )
        lineTo(left + radius, right - radius)
        arcToRadius(
                right - 2 * radius,
                bottom - 2 * radius,
                radius,
                90f,
                -90f
        )
        lineTo(right, top + radius)
        arcToRadius(
                right - 2 * radius,
                top,
                radius,
                0f,
                -90f
        )
        close()
    }, paint)
}
