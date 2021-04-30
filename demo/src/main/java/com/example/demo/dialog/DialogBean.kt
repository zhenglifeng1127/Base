package com.example.demo.dialog

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue


abstract class DialogBean {
    abstract var title: String
    abstract var content: String
    abstract val show: MutableState<Boolean>
}

abstract class DialogBtn {

    abstract val isSingle: Boolean

    abstract val cancelText: String

    abstract val checkText: String

    abstract val onConfirm: () -> Unit

    abstract val onDismiss: () -> Unit
}

/**
 * @param cancelText = 取消按钮文本
 *
 * @param checkText = 确认按钮文本
 *
 * @param onConfirm = 确认按钮点击事件
 *
 * @param onDismiss = 取消按钮点击事件
 *
 * @param isSingle 是否只有单个确认键,true：onDismiss,cancelText不显示
 *
 */
data class DialogBtnBean(
    override val isSingle: Boolean = false,
    override val cancelText: String = "取消",
    override val checkText: String = "确认",
    override val onConfirm: () -> Unit = {},
    override val onDismiss: () -> Unit = {}
) : DialogBtn()


/**
 * 弹出框数据实体
 *
 * @param title 标题
 *
 * @param content 内容
 *

 * @param show 弹出框显示控制器
 *

 *
 */
class NormalDialogBean(
    override var title: String,
    override var content: String,
    override val show: MutableState<Boolean>,
    val btn: DialogBtnBean = DialogBtnBean()
) : DialogBean()


class InputDialogBean(
    override var title: String,
    override var content: String = "",
    override val show: MutableState<Boolean>,
    var inputText: MutableState<TextFieldValue>,

    val btn: DialogBtnBean = DialogBtnBean(
        false, "取消", "确认", {}, {
            show.value = false
        }
    )
) : DialogBean()