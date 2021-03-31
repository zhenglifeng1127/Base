package com.android.base.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.android.base.R
import com.android.base.config.AppManager
import com.android.base.ui.LoadingPopup
import com.android.base.utils.other.PopupGlideLoader
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupPosition
import com.lxj.xpopup.impl.ConfirmPopupView
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.OnInputConfirmListener
import com.lxj.xpopup.interfaces.OnSelectListener


fun AppManager.popup(): XPopup.Builder {
    return endOfStack().dialog()
}

fun Activity.dialog(): XPopup.Builder {
    return XPopup.Builder(AppManager.endOfStack())
}

fun XPopup.Builder.asAppConfirm(
    title: String,
    content: String,
    confirmListener: OnConfirmListener
): ConfirmPopupView {
    return asConfirm(
        title,
        content,
        null,
        null,
        confirmListener,
        null,
        false
    ).bindLayout(R.layout.dialog_xpopup_center_impl_confirm)
}

fun XPopup.Builder.asAppConfirm(
    title: String,
    content: String,
    cancel: String,
    confirm: String,
    confirmListener: OnConfirmListener,
    cancelListener: OnCancelListener
): ConfirmPopupView {
    return asConfirm(
        title,
        content,
        cancel,
        confirm,
        confirmListener,
        cancelListener,
        false
    ).bindLayout(
        R.layout.dialog_xpopup_center_impl_confirm
    )
}

/**
 * 常规弹出样式
 */
fun XPopup.Builder.normal(title: String = "温馨提示", content: String, listener: OnConfirmListener) {
    asAppConfirm(
        title, content, listener
    ).show()
}

/**
 * 输入框
 */
fun XPopup.Builder.input(
    title: String,
    content: String?,
    inputContent: String,
    hint: String,
    listener: OnInputConfirmListener
) {
    asInputConfirm(title, content, inputContent, hint, listener).show()
}


/**
 * 常规弹出样式
 */
fun XPopup.Builder.normal(
    title: String = "温馨提示",
    content: String,
    cancel: String,
    confirm: String,
    listener: OnConfirmListener,
    cancelListener: OnCancelListener
) {
    asAppConfirm(
        title, content, cancel, confirm, listener, cancelListener
    ).show()
}

/**
 * 依附VIEW显示简单列表，PartShadowPopupView可制作筛选效果
 */
fun XPopup.Builder.attach(
    v: View,
    title: Array<String>,
    icon: IntArray?,
    listener: OnSelectListener
) {
    atView(v)
        .asAttachList(
            title,
            icon,
            listener
        ).show()
}

/**
 * 依附VIEW显示简单列表
 * @param popup 传入自定义popup
 */
fun XPopup.Builder.attach(v: View, popup: BasePopupView?) {
    popup?.let {
        atView(v)
            .asCustom(it)
            .show()
    }
}

/**
 * 大图展示
 */
fun XPopup.Builder.img(img: ImageView, position: Int = 0, data: List<Any>?) {
    asImageViewer(
        img, position, data,
        { popupView, _ ->
            // 作用是当Pager切换了图片，需要更新源View
            popupView.updateSrcView(img)
        }, PopupGlideLoader()
    ).show()
}

/**
 * 显示已有popup
 */
fun XPopup.Builder.showPopup(
    popup: BasePopupView?,
    isDismiss: Boolean = true,
    isMoveUpToKeyboard: Boolean = false
) {
    popup?.let {
        dismissOnBackPressed(isDismiss) // 按返回键是否关闭弹窗，默认为true
            .dismissOnTouchOutside(isDismiss) // 点击外部是否关闭弹窗，默认为true
            .moveUpToKeyboard(isMoveUpToKeyboard)
            .asCustom(it)
            .show()
    }
}

/**
 * 显示loading
 */
fun XPopup.Builder.showLoad(): LoadingPopup {
    val popup = LoadingPopup()
    autoOpenSoftInput(true)
        .asCustom(popup)
        .show()
    return popup
}


/**
 * 右侧显示
 */
fun XPopup.Builder.showRightPopup(popup: BasePopupView) {
    popupPosition(PopupPosition.Right)//右边
        .hasStatusBarShadow(true) //启用状态栏阴影
        .asCustom(popup)
        .show()
}
