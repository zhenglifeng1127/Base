package com.example.demo.banner.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.demo.banner.base.*
import com.example.demo.banner.config.BannerConfig
import com.example.demo.banner.indicator.CircleIndicator
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "BannerPager"

@Composable
fun <T : BaseBannerBean> BannerPager(
    modifier: Modifier = Modifier,
    items: MutableList<T> = arrayListOf(),
    config: BannerConfig = BannerConfig(),
    indicator: Indicator = CircleIndicator(),
    transformer: BasePageTransformer? = null,
    onBannerClick: (T) -> Unit
) {
    if (items.isEmpty()) {
        throw NullPointerException("items is not null")
    }

    val pagerState: PagerState = remember { PagerState() }
    pagerState.setTransformer(transformer)
    pagerState.maxPage = (items.size - 1).coerceAtLeast(0)

    if (config.repeat) {
        StartBanner(pagerState, config.intervalTime)
    }

    Box(modifier = modifier.height(config.bannerHeight)) {
        Pager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().height(config.bannerHeight)
        ) {
            val item = items[page]
            BannerCard(
                bean = item,
                modifier = Modifier.fillMaxSize().padding(config.bannerImagePadding),
                shape = config.shape
            ) {
                Log.d(TAG, "item is :${item.javaClass}")
                onBannerClick(item)
            }
        }

        indicator.DrawIndicator(pagerState)
    }
}

var mTimer: Timer? = null
var mTimerTask:TimerTask? = null

@Composable
fun StartBanner(pagerState: PagerState, intervalTime: Long) {
    val coroutineScope = rememberCoroutineScope()
    mTimer?.cancel()
    mTimerTask?.cancel()
    mTimer = Timer()
    mTimerTask = object : TimerTask() {
        override fun run() {
            coroutineScope.launch {
                pagerState.setNextPage()
            }
        }
    }
    mTimer?.schedule(mTimerTask, intervalTime, intervalTime)
}


