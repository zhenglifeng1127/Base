package com.android.base.utils.other

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.text.style.ReplacementSpan
import com.android.base.R
import com.android.base.config.AppManager
import com.android.base.utils.getDimen
import com.android.base.utils.getResColor
import com.android.base.utils.px2dp


class RoundBackgroundColorSpan : ReplacementSpan() {




    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val originalColor = paint.color
        val px4 = AppManager.endOfStack().getDimen(R.dimen.dp_2)
        val topNow = (top + AppManager.endOfStack().px2dp(3f)).toFloat()
        val rightNow = x + (paint.measureText(
            text,
            start,
            end
        ).toInt() + AppManager.endOfStack().px2dp(16f))
        val bottomNow = (bottom - AppManager.endOfStack().px2dp(1f)).toFloat()



        val path = Path()
        val path1 = Path()
        paint.color = AppManager.endOfStack().getResColor(R.color.gradient_black_start)
        val rect = RectF(
            x,
            topNow
            , rightNow
            , bottomNow / 2
        )
        val rect1 = RectF(
            x,
            bottomNow / 2
            , rightNow
            , bottomNow
        )
        path.addRoundRect(
            rect,
            floatArrayOf(px4, px4, px4, px4,0f, 0f, 0f, 0f),
            Path.Direction.CCW
        )
        canvas.drawPath(path, paint)
        paint.color = AppManager.endOfStack().getResColor(R.color.gradient_black_end)
        path1.addRoundRect(
            rect1,
            floatArrayOf(0f, 0f, 0f, 0f,px4, px4, px4, px4),
            Path.Direction.CCW
        )
        canvas.drawPath(path1, paint)
        paint.color = AppManager.endOfStack().getResColor(R.color.white)
        canvas.drawText(
            text,
            start,
            end,
            x + AppManager.endOfStack().px2dp(8f),
            y.toFloat(),
            paint
        )

        paint.color = originalColor

    }

}