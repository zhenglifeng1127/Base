package com.example.demo.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.android.base.config.AppManager
import com.android.base.utils.toast

@Composable
fun showDialogDemo() {
    val show = remember {
        mutableStateOf(false)
    }

    val showLoad = remember {
        mutableStateOf(false)
    }

    val showText = remember {
        mutableStateOf(false)
    }

    val inputText =  remember {
        mutableStateOf(TextFieldValue(""))
    }
    Surface(
        modifier = Modifier
            .width(375.dp)
            .fillMaxHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "普通弹出框", modifier = Modifier.clickable(onClick = {
                show.value = true
            }))

            Text(text = "加载框", modifier = Modifier.clickable(onClick = {
                showLoad.value = true
            }))

            Text(text = "文本输入框", modifier = Modifier.clickable(onClick = {
                showText.value = true
            }))
        }


    }

    NormalDialog(
        NormalDialogBean(
            title = "测试标题",
            content = "测试内容",
            show = show,
            btn = DialogBtnBean(onConfirm = {
                AppManager.endOfStack().toast("确认点击")
//                show.value = false
            })
        )
    )

    InputDialog(
        InputDialogBean(
            title = "测试标题",
            show = showText,
            btn = DialogBtnBean(onConfirm = {

                AppManager.endOfStack().toast(inputText.value.text)
            }),
            inputText = inputText
        )
    )
    LoadDialog(show = showLoad)
}


