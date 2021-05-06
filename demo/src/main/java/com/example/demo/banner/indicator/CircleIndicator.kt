package com.example.demo.banner.indicator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.demo.banner.base.BannerGravity.BottomCenter
import com.example.demo.banner.base.BannerGravity.BottomLeft
import com.example.demo.banner.base.BannerGravity.BottomRight
import com.example.demo.banner.base.Indicator
import com.example.demo.banner.base.PagerState

class CircleIndicator(
    var indicatorColor: Color = Color(30, 30, 33, 90),
    var selectIndicatorColor: Color = Color.Green,
    var indicatorDistance: Int = 50,
    var indicatorSize: Float = 10f,
    var selectIndicatorSize: Float = 13f,
    override var gravity: Int = BottomCenter,
) : Indicator() {

    @Composable
    override fun DrawIndicator(pagerState: PagerState) {
        for (pageIndex in 0..pagerState.maxPage) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val color: Color
                val inSize: Float
                if (pageIndex == pagerState.currentPage) {
                    color = selectIndicatorColor
                    inSize = selectIndicatorSize
                } else {
                    color = indicatorColor
                    inSize = indicatorSize
                }
                val start = when (gravity) {
                    BottomCenter -> {
                        val width = canvasWidth - pagerState.maxPage * indicatorDistance
                        width / 2
                    }
                    BottomLeft -> {
                        100f
                    }
                    BottomRight -> {
                        canvasWidth - pagerState.maxPage * indicatorDistance - 100f

                    }
                    else -> 100f
                }
                drawCircle(
                    color,
                    inSize,
                    center = Offset(start + pageIndex * indicatorDistance, canvasHeight - 50f)
                )
            }
        }
    }
}