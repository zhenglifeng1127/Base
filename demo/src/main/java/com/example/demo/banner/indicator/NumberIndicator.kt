package com.example.demo.banner.indicator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo.banner.base.BannerGravity
import com.example.demo.banner.base.BannerGravity.BottomRight
import com.example.demo.banner.base.Indicator
import com.example.demo.banner.base.PagerState

class NumberIndicator(
    var backgroundColor: Color = Color(30, 30, 33, 90),
    var numberColor: Color = Color.White,
    var circleSize: Dp = 35.dp,
    var fontSize: TextUnit = 15.sp,
    override var gravity: Int = BottomRight
): Indicator() {

    @Composable
    override fun DrawIndicator(pagerState: PagerState) {
        val alignment: Alignment = when (gravity) {
            BannerGravity.BottomCenter -> {
                Alignment.BottomCenter
            }
            BannerGravity.BottomLeft -> {
                Alignment.BottomStart
            }
            BottomRight -> {
                Alignment.BottomEnd
            }
            else -> Alignment.BottomEnd
        }
        Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = alignment) {
            Box(
                modifier = Modifier.size(circleSize).clip(CircleShape)
                    .background(color = backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "${pagerState.currentPage + 1}/${pagerState.maxPage + 1}",
                    color = numberColor,
                    fontSize = fontSize
                )
            }
        }
    }
}