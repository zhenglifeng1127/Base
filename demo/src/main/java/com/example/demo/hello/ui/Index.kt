package com.example.demo.hello.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.android.base.config.ActivityConfig
import com.example.demo.R
import com.example.demo.banner.ui.BannerPager
import com.example.demo.title.LocationBar
import kotlin.collections.ArrayList

@ExperimentalAnimationApi
@Composable
fun Index(modifier: Modifier, config: ActivityConfig) {
    val colorState = remember {
        mutableStateOf(R.color.org)
    }
    val calendarVisible = remember {
        mutableStateOf(false)
    }
    val cityName = remember {
        mutableStateOf("温州市鹿城区")
    }


    Column {
        LocationBar(config = config, cityName = cityName)
        Surface(color = colorResource(id = R.color.border), modifier = modifier) {
            Box(
                Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {

                AnimVisibleView(visible = calendarVisible, isCalendar = true)
                AnimVisibleView(visible = calendarVisible, isCalendar = false)
                Button(
                    onClick = {
                        calendarVisible.value = !calendarVisible.value
                        colorState.value = if (calendarVisible.value) {
                            R.color.deliveryBlue
                        } else {
                            R.color.org
                        }

                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = colorState.value)
                    ),
                    modifier = Modifier
                        .width(45.dp)
                        .height(80.dp)
                        .padding(end = 5.dp, bottom = 40.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {


                }


            }
        }


    }

}

@ExperimentalAnimationApi
@Composable
fun AnimVisibleView(visible: MutableState<Boolean>, isCalendar: Boolean) {
    Column {
        if (isCalendar) {
            AnimatedVisibility(
                visible = !visible.value,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut()
            ) {
                CityCalendar()
            }
        } else {
            AnimatedVisibility(
                visible = visible.value,
                enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
            ) {
                AdBanner()
            }
        }
    }
}




@Composable
fun AdBanner() {

    val list = ArrayList<BannerBean>().apply {
        add(BannerBean(R.mipmap.banner))
        add(BannerBean(R.mipmap.banner2))
    }
    Surface(
        color = colorResource(id = R.color.transport)
    ) {
        BannerPager(
            modifier = Modifier.fillMaxSize(),
            items = list
        ) {

        }
    }
}

