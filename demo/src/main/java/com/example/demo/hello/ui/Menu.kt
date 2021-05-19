package com.example.demo.hello.ui

import android.content.ContentValues
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun TopIconItem(
    url: Any?,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    content: @Composable ()->Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        NetImage(url = url,imageModifier = imageModifier)
        content()

    }


}

@Composable
fun NetImage( url: Any?,    imageModifier: Modifier = Modifier){
    when (url) {
        is String -> {
            if (url.contains("https://") || url.contains("http://")) {
                Log.d(ContentValues.TAG, "PostCardPopular: 加载网络图片")
                Image(
                    modifier = imageModifier,
                    painter = rememberCoilPainter(request = url),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                Log.d(ContentValues.TAG, "PostCardPopular: 加载本地图片")
                val bitmap = BitmapFactory.decodeFile(url)
                Image(
                    modifier = imageModifier,
                    painter = BitmapPainter(bitmap.asImageBitmap()),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
        }
        is Int -> {
            Log.d(ContentValues.TAG, "PostCardPopular: 加载本地资源图片")
            Image(
                modifier = imageModifier,
                painter = painterResource(url),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        else -> {
            throw IllegalArgumentException("参数类型不符合要求，只能是：url、文件路径或者是 drawable id")
        }
    }
}


@Composable
fun IndexMainMenuItem(data: TopIconData) {
    TopIconItem(
        url = data.url,
        modifier = Modifier
            .width(80.dp)
            .fillMaxHeight(),
        imageModifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(30.dp)),
    ){
        Text(text = data.text,modifier =Modifier.padding(top = 4.dp) )
    }
}


@Composable
fun IndexSubscribeMenuItem(data: TopIconData){
    TopIconItem(
        url = data.url,
        modifier = Modifier
            .width(80.dp)
            .fillMaxHeight()
            .padding(top = 10.dp, bottom = 10.dp),
        imageModifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(8.dp)),
    ){
        Text(text = data.text,modifier =Modifier.padding(top = 4.dp),fontSize = 12.sp)
    }
}

@Composable
fun IndexRecoMenuItem(data: TopIconData){
    Box(modifier = Modifier.height(80.dp).fillMaxWidth().padding(top = 15.dp,start = 7.5.dp,end = 7.5.dp),contentAlignment = Alignment.Center){
        NetImage(url =data.url,imageModifier =Modifier.fillMaxSize().clip(RoundedCornerShape(10.dp)))
    }
}


data class TopIconData(val url:Any?,val text:String)