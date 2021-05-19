package com.example.demo.hello.ui

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.base.application.BaseApplication
import com.android.base.utils.*
import com.example.demo.R
import java.util.*
import kotlin.collections.ArrayList

/**
 * 日历主要布局
 */
@ExperimentalAnimationApi
@Composable
fun CityCalendar() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp)),
        elevation = 2.dp,
        color = colorResource(id = R.color.white)
    ) {
        val cal = Calendar.getInstance()

        val day = cal.day()

        val monthNow = cal.month()

        val yearNow = cal.year()

        val dataList = cal.getWeekOfNow()

        val viewData = ArrayList<CityCalendarBean>()

        val clickIndex = remember {
            mutableStateOf(-1)
        }

        val nextState = remember {
            mutableStateOf(false)
        }
        val task = Timer()
        task.schedule(object : TimerTask() {
            override fun run() {
                nextState.value = !nextState.value
            }
        }, 1000, 4000)


        viewData.add(
            CityCalendarBean(
                weekName = "日",
                dateName = dataList[0],
                index = 0,
                isNow = dataList[0].toInt() == day,
                offsetState = remember {
                    mutableStateOf(false)
                }, activeList = ArrayList<String>().apply {
                    add("测试活动1")
                })
        )
        viewData.add(
            CityCalendarBean(
                weekName = "一",
                dateName = dataList[1],
                index = 1,
                isNow = dataList[1].toInt() == day,
                offsetState = remember {
                    mutableStateOf(false)
                }, activeList = ArrayList<String>().apply {
                    add("测试活动1")
                    add("测试活动2")
                })
        )
        viewData.add(
            CityCalendarBean(
                weekName = "二",
                dateName = dataList[2],
                index = 2,
                isNow = dataList[2].toInt() == day,
                offsetState = remember {
                    mutableStateOf(false)
                }, activeList = ArrayList()
            )
        )
        viewData.add(
            CityCalendarBean(
                weekName = "三",
                dateName = dataList[3],
                index = 3,
                isNow = dataList[3].toInt() == day,
                offsetState = remember {
                    mutableStateOf(false)
                }, activeList = ArrayList()
            )
        )
        viewData.add(
            CityCalendarBean(
                weekName = "四",
                dateName = dataList[4],
                index = 4,
                isNow = dataList[4].toInt() == day,
                offsetState = remember {
                    mutableStateOf(false)
                }, activeList = ArrayList<String>().apply {
                    add("测试活动1")
                    add("测试活动2")
                })
        )
        viewData.add(
            CityCalendarBean(
                weekName = "五",
                dateName = dataList[5],
                index = 5,
                isNow = dataList[5].toInt() == day,
                offsetState = remember {
                    mutableStateOf(false)
                }, activeList = ArrayList<String>().apply {
                    add("测试活动1")
                })
        )
        viewData.add(
            CityCalendarBean(
                weekName = "六",
                isNow = dataList[6].toInt() == day,
                dateName = dataList[6],
                index = 6,
                offsetState = remember {
                    mutableStateOf(false)
                }, activeList = ArrayList<String>().apply {
                    add("测试活动2")
                })
        )
        val clickState = remember {
            mutableStateOf(false)
        }

        ConstraintLayout {
            val (dateText, calendarNext, dateColumn, activeColumn, activeBottom) = createRefs()
            Text(
                text = getCalenderText(dataList, monthNow, yearNow),
                modifier = Modifier
                    .constrainAs(dateText) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .padding(start = 10.dp, top = 5.dp)
            )

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.blue3A)
                ),
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 10.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .constrainAs(calendarNext) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            ) {

            }

            val offsetX = remember {
                mutableStateOf(IntOffset(0, 0))
            }
            val offset = animateIntOffsetAsState(targetValue = offsetX.value)

            Surface(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(end = 43.dp, top = 10.dp)
                .constrainAs(dateColumn) {
                    start.linkTo(parent.start)
                    top.linkTo(dateText.bottom)
                }
                .offsetDp { offset.value }) {
                WeekItem(
                    data = viewData,
                    offsetX = offsetX,
                    state = clickState,
                    clickIndex = clickIndex
                )
            }


            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(end = 43.dp, top = 10.dp, start = 46.dp)
                    .constrainAs(activeColumn) {
                        start.linkTo(parent.start)
                        top.linkTo(dateText.bottom)
                    }
                    .clickable(onClick = {
                        clickState.value = false
                        viewData[clickIndex.value].offsetState.value = false
                        clickIndex.value = -1
                        offsetX.value = IntOffset(0, 0)
                    })
            ) {
                AnimatedVisibility(
                    visible = clickState.value,
                    enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                    exit = slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
                ) {
                    Surface(color = Color.White) {

                        Row(
                            modifier = Modifier
                                .height(70.dp)
                                .fillMaxWidth()

                        ) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                                    .padding(top = 10.dp, bottom = 10.dp)
                            )
                            if (clickIndex.value != -1 && viewData[clickIndex.value].activeList.size > 0) {
                                ActiveBody(viewData[clickIndex.value].activeList)
                            } else
                                ActiveEmpty()
                        }

                    }

                }


            }

            Surface(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .constrainAs(activeBottom) {
                        top.linkTo(dateColumn.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(dateText.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 8.dp, bottom = 8.dp, end = 60.dp, start = 10.dp)
                    .clip(
                        RoundedCornerShape(
                            10.dp
                        )
                    ),
                color = Color.LightGray
            ) {
                Box(
                    contentAlignment = Alignment.CenterStart,

                ) {
                    AnimatedVisibility(
                        visible = nextState.value,
                        enter = slideInVertically(initialOffsetY = { it / 2 }) + expandVertically(),
                        exit = slideOutVertically(targetOffsetY = { -it / 2 }) + fadeOut(),
                    ) {
                        Box(contentAlignment = Alignment.CenterStart) {
                            Text(
                                text = "测试内容",
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            )
                        }
                    }
                    AnimatedVisibility(
                        visible = !nextState.value,
                        enter = slideInVertically(initialOffsetY = { it / 2 }) + expandVertically(),
                        exit = slideOutVertically(targetOffsetY = { -it / 2 }) + fadeOut(),
                    ) {
                        Box(contentAlignment = Alignment.CenterStart) {
                            Text(
                                text = "测试内容1",
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp)
                            )
                        }
                    }
                }

            }

        }
    }

}

@Composable
fun ActiveBody(activeList: MutableList<String>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Image(painter =  painterResource(id = R.mipmap.ic_launcher_round),contentDescription = null)
        when (activeList.size) {
            1 -> {
                Box(
                    contentAlignment = Alignment.CenterStart, modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {

                        })
                ) {
                    Text(
                        text = activeList[0],
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.weight(1f)) {
                    Text(
                        text = "今天暂无其它活动",
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }


            }
            else -> {
                Box(
                    contentAlignment = Alignment.CenterStart, modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {

                        })
                ) {
                    Text(
                        text = activeList[0],
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Box(
                    contentAlignment = Alignment.CenterStart, modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {

                        })
                ) {
                    Text(
                        text = activeList[1],
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun ActiveEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Image(painter =  painterResource(id = R.mipmap.ic_launcher_round),contentDescription = null)
        Text(text = "今天暂无活动")
    }
}


/**
 * 此处偏移量算法以375.dp为基准,日历周布局
 */
@ExperimentalAnimationApi
@Composable
fun WeekItem(
    offsetX: MutableState<IntOffset>,
    data: MutableList<CityCalendarBean>,
    clickIndex: MutableState<Int>,
    state: MutableState<Boolean>,
) {

    Row {

        data.forEach {
//            WeekItem(
//                data = it,
//                offsetX = offsetX,
//                state = clickState,
//                clickIndex = clickIndex
//            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(70.dp)
                    .clickable(onClick = {
                        it.offsetState.value = !it.offsetState.value
                        state.value = it.offsetState.value
                        if (it.offsetState.value) {
                            clickIndex.value = it.index
                            offsetX.value = IntOffset(
                                if (clickIndex.value == 0 || clickIndex.value == -1) {
                                    0
                                } else {
                                    -46 * clickIndex.value
                                }, 0
                            )
                        } else {
                            offsetX.value = IntOffset(0, 0)
                        }
                    }),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!it.offsetState.value) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = it.weekName,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    WeekChildBg(isNow = it.isNow, isActive = it.activeList.size > 0) {
                        WeekChildItem(
                            data = it
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "周${it.weekName}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = it.dateName,
                        modifier = Modifier.width(20.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        maxLines = 1
                    )
                }
            }
        }
    }


}


/**
 * 日历周背景
 */
@Composable
fun WeekChildBg(
    isNow: Boolean = false,
    isActive: Boolean = false,
    content: @Composable() () -> Unit
) {
    if (isNow) {
        Surface(
            modifier = Modifier
                .width(20.dp)
                .height(if (isActive) 70.dp else 25.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = Color.Blue
        ) {
            content()
        }
    } else {
        content()
    }

}

/**
 * 日历周子项布局
 */
@Composable
fun WeekChildItem(data: CityCalendarBean) {
    Column {
        Text(
            text = data.dateName,
            modifier = Modifier.width(20.dp),
            textAlign = TextAlign.Center,
            color = if (data.isNow) Color.White else Color.Black
        )
        if (data.activeList.size > 0) {

            Canvas(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                if (data.activeList.size == 1) {
                    drawCircle(
                        if (data.isNow) Color.White else Color.Blue,
                        4f,
                        center = Offset(canvasWidth / 2, canvasHeight / 2)
                    )
                } else {
                    drawCircle(
                        if (data.isNow) Color.White else Color.Blue,
                        4f,
                        center = Offset(canvasWidth / 4, canvasHeight / 2)
                    )
                    drawCircle(
                        if (data.isNow) Color.White else Color.Blue,
                        4f,
                        center = Offset(canvasWidth * 3 / 4, canvasHeight / 2)
                    )
                }
            }

        }
    }
}

/**
 * 获取最近一个周区间
 */
private fun getCalenderText(dataList: MutableList<String>, monthNow: Int, yearNow: Int): String {
    return if (dataList[0].toInt() > 20 && dataList[6].toInt() < 10) {
        if (monthNow == 1) {
            "${yearNow - 1}年${monthNow - 1}月${dataList[0]}日~${yearNow}年${monthNow}月${dataList[6]}日"
        } else {
            "${yearNow}年${monthNow - 1}月${dataList[0]}日~${monthNow}月${dataList[6]}日"
        }
    } else {
        "${yearNow}年${monthNow}月${dataList[0]}日~${monthNow}月${dataList[6]}日"
    }
}

data class CityCalendarBean(
    val weekName: String,
    val dateName: String,
    val index: Int,
    val isNow: Boolean = false,
    val activeList: MutableList<String>,
    val offsetState: MutableState<Boolean>
)

fun Modifier.offsetDp(offset: Density.() -> IntOffset) = this.then(

    OffsetPxModifierDp(
        offset = offset,
        rtlAware = true,
        inspectorInfo = debugInspectorInfo {
            name = "offset"
            properties["offset"] = offset
        }
    )
)


private class OffsetPxModifierDp(
    val offset: Density.() -> IntOffset,
    val rtlAware: Boolean,
    inspectorInfo: InspectorInfo.() -> Unit
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {

        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            val offsetValue = offset()
            if (rtlAware) {
                placeable.placeRelativeWithLayer(offsetValue.x.dp.roundToPx(), offsetValue.y)
            } else {
                placeable.placeWithLayer(offsetValue.x.dp.roundToPx(), offsetValue.y)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? OffsetPxModifierDp ?: return false

        return offset == otherModifier.offset &&
                rtlAware == otherModifier.rtlAware
    }

    override fun hashCode(): Int {
        var result = offset.hashCode()
        result = 31 * result + rtlAware.hashCode()
        return result
    }

    override fun toString(): String = "OffsetPxModifier(offset=$offset, rtlAware=$rtlAware)"
}
