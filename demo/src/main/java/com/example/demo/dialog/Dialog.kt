package com.example.demo.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * 基本双按钮弹框
 */
@Composable
fun NormalDialog(data: NormalDialogBean) {
    if (data.show.value) {
        AlertDialog(onDismissRequest = { data.show.value = false },
            text = {
                Text(text = data.content)

            }, title = {
                Text(
                    text = data.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }, buttons = {
                DialogBtn(data = data.btn, show = data.show)
            })
    }
}

/**
 * 按钮控制器
 */
@Composable
fun DialogBtn(data: DialogBtnBean, show: MutableState<Boolean>, isInput:Boolean = false, textFieldValue: MutableState<TextFieldValue>? = null) {
    Column {
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = data.checkText,
                color = Color.Blue,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding((16.dp))
                    .clickable(onClick = {
                        data.onConfirm()
                        show.value = false
                        if(isInput){
                            textFieldValue?.value = TextFieldValue("")
                        }
                    })
                    .weight(1f)
            )
            if (!data.isSingle) {
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Text(
                    text = data.cancelText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding((16.dp))
                        .clickable(onClick = {
                            data.onDismiss()
                            show.value = false
                            if(isInput){
                                textFieldValue?.value = TextFieldValue("")
                            }
                        })
                        .weight(1f)
                )
            }

        }
    }
}

/**
 * 加载
 */
@Composable
fun LoadDialog(show: MutableState<Boolean>) {
    if (show.value) {
        AlertDialog(
            onDismissRequest = { show.value = false },
            text = {
                CircularProgressIndicator()
            },
            buttons = {

            }
        )
    }

}

/**
 * 输入式弹出框
 */
@Composable
fun InputDialog(data: InputDialogBean) {
    if (data.show.value) {

        AlertDialog(
            title = {
                Text(
                    text = data.title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            onDismissRequest = { data.show.value = false
                data.inputText.value = TextFieldValue("")
            },
            text = {
                //撑开顶部距离，暂时找不到其他可以撑开的控件
                Text(
                    text = "",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(value = data.inputText.value, onValueChange = {
                    data.inputText.value = it
                }, placeholder = {
                    Text(text = "再此处输入内容")
                })

            },
            buttons = {
                DialogBtn(data = data.btn, show = data.show,isInput = true,data.inputText)
            }
        )
    }
}
