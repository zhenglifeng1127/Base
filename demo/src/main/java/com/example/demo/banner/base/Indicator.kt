package com.example.demo.banner.base

import androidx.compose.runtime.Composable

abstract class Indicator {
    abstract var gravity: Int

    @Composable
    abstract fun DrawIndicator(pagerState: PagerState)
}