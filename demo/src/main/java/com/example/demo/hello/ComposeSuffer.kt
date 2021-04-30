package com.example.demo.hello

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.base.config.AppManager
import com.android.base.utils.openNext
import com.example.demo.dialog.DialogActivity



@Composable
fun rootSuffer(){

    Surface(modifier = Modifier.width(375.dp)) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Surface(modifier = Modifier
                .height(55.dp)
                .width(55.dp)
                .clickable(onClick = {
                    AppManager.endOfStack().openNext<DialogActivity>()
                }),color = Color.Green) {
                Text(
                    text = "弹出框样例"
                )
            }
            Surface(modifier = Modifier
                .height(55.dp)
                .width(55.dp),color = Color.Green) {
                Text(
                    text = "Second item"
                )
            }
            Surface(modifier = Modifier
                .height(55.dp)
                .width(55.dp),color = Color.Green) {
                Text(
                    text = "Second item"
                )
            }
            Surface(modifier = Modifier
                .height(55.dp)
                .width(55.dp),color = Color.Green) {
                Text(
                    text = "Second item"
                )
            }
            Surface(modifier = Modifier
                .height(55.dp)
                .width(55.dp),color = Color.Green) {
                Text(
                    text = "Second item"
                )
            }
            Surface(modifier = Modifier
                .height(55.dp)
                .width(55.dp),color = Color.Green) {
                Text(
                    text = "Second item"
                )
            }

        }
    }
}