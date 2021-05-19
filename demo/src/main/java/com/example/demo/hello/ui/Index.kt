package com.example.demo.hello.ui

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.android.base.config.ActivityConfig
import com.example.demo.R
import com.example.demo.banner.ui.BannerPager
import com.example.demo.title.LocationBar
import kotlin.collections.ArrayList

@ExperimentalFoundationApi
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
    Column(modifier = modifier) {
        LocationBar(config = config, cityName = cityName)

        LazyColumn (modifier = Modifier
            .fillMaxWidth()
            .weight(1f)){
            item {
                //banner
                Box(
                    Modifier
                        .background(colorResource(id = R.color.border))
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
            item {
                val list: MutableList<TopIconData> = remember {
                    mutableStateListOf()
                }

                list.apply {
                    add(TopIconData(R.mipmap.icon_menu, "停车宝"))
                    add(TopIconData(R.mipmap.icon_menu, "查学区"))
                    add(TopIconData(R.mipmap.icon_menu, "借图书"))
                    add(TopIconData(R.mipmap.icon_menu, "搜好玩"))
                }
                //主应用
                LazyVerticalGrid(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                    cells = GridCells.Fixed(4),
                    content = {
                        items(list) {
                            IndexMainMenuItem(it)
                        }
                    }
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(start = 15.dp, end = 15.dp)
                )
            }
            item {
                val recommendList: MutableList<TopIconData> = remember {
                    mutableStateListOf()
                }

                recommendList.apply {
                    add(TopIconData(R.mipmap.icon_menu, "趣农庄"))
                    add(TopIconData(R.mipmap.icon_menu, "走乡下"))
                    add(TopIconData(R.mipmap.icon_menu, "排路线"))
                    add(TopIconData(R.mipmap.icon_menu, "要移车"))
                    add(TopIconData(R.mipmap.icon_menu, "非遗厅"))
                    add(TopIconData(R.mipmap.icon_menu, "更多"))
                }
//            //次要订阅内容
                LazyVerticalGrid(
                    modifier = Modifier
                        .height(145.dp)
                        .fillMaxWidth(),
                    cells = GridCells.Fixed(4),
                    content = {
                        items(recommendList) {
                            IndexSubscribeMenuItem(it)
                        }
                    }
                )
            }
            item {
                Surface(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .height(70.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    color = colorResource(id = R.color.white)
                ) {
                    Row {

                    }
                }
            }
            item {
                val recommendList: MutableList<TopIconData> = remember {
                    mutableStateListOf()
                }

                recommendList.apply {
                    add(TopIconData(R.mipmap.banner, "塘河夜画"))
                    add(TopIconData(R.mipmap.banner, "瓯江夜游"))
                    add(TopIconData(R.mipmap.banner, "亲子游"))
                    add(TopIconData(R.mipmap.banner, "想休闲"))
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "专题推荐", modifier = Modifier.padding(start = 15.dp))
                }
                LazyVerticalGrid(cells = GridCells.Fixed(2),
                    content = {
                        items(recommendList) {
                            IndexRecoMenuItem(it)
                        }
                    },
                    modifier = Modifier.height(175.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(start = 7.5.dp,end = 7.5.dp,bottom = 15.dp)
                )
            }
            item {
                Row {
                    Text(text = "热点精选")
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

