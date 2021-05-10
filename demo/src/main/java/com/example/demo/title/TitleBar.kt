package com.example.demo.title

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.android.base.application.BaseApplication
import com.android.base.config.ActivityConfig
import com.android.base.utils.getScreenHeight

@Preview(showBackground = true)
@Composable
fun NormalBar(icon: ImageVector = Icons.Filled.ArrowBack, title: String = "这个是标题") {
    TopAppBar(backgroundColor = Color.White) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberVectorPainter(image = icon),
                contentDescription = "",
                modifier = Modifier.fillMaxHeight()
            )
            Text(text = title, modifier = Modifier.align(Alignment.Center), color = Color.Black)
        }

    }
}

@Composable
fun LocationBar(
    config: ActivityConfig,
    cityName: MutableState<String>,
    backgroundColor: Color = Color.White,
    icon: ImageVector = Icons.Filled.ArrowDropDown
) {
    GetAppBar(config = config, color = backgroundColor) {
        TopAppBar(
            backgroundColor = backgroundColor,
            elevation = 0.dp
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (locationName, locationIcon, searchBg, msgIcon) = createRefs()
                Text(

                    text = cityName.value,
                    color = Color.Black,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(50.dp)
                        .constrainAs(locationName) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(start = 5.dp)
                )
                Image(
                    painter = rememberVectorPainter(image = icon),
                    contentDescription = "",
                    modifier = Modifier
                        .constrainAs(locationIcon) {
                            start.linkTo(locationName.end)
                            top.linkTo(locationName.top)
                            bottom.linkTo(locationName.bottom)
                        }
                )
                Surface(
                    Modifier
                        .width(260.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .constrainAs(searchBg) {
                            start.linkTo(locationIcon.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    color = Color.LightGray
                ) {

                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberVectorPainter(image = Icons.Filled.Search),
                            contentDescription = "",
                        )
                        Text(
                            text = "搜索",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(start = 5.dp)
                        )
                    }

                }

                Image(
                    painter = rememberVectorPainter(image = Icons.Filled.Menu),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(msgIcon) {
                        end.linkTo(parent.end)
                        start.linkTo(searchBg.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )
            }
        }
    }
}


/**
 * 沉浸式状态栏处理配合BaseActivity
 */
@Composable
fun GetAppBar(
    config: ActivityConfig,
    color: Color = Color.White,
    content: @Composable() () -> Unit
) {
    if (config.getTranslucentOpen()) {
        Surface(modifier = Modifier.fillMaxWidth(), color = color) {
            Column {
                Spacer(modifier = Modifier.height(28.dp))
                content()
            }
        }
    } else {
        content()
    }
}