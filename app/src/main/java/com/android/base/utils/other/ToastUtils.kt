package com.android.base.utils.other

import android.view.Gravity
import android.widget.Toast
import com.android.base.config.AppManager


class ToastUtils{

    companion object {
        private lateinit var mToast: Toast
        /**
         * 默认
         * @param msg 内容
         */
        fun show(msg: String) {
            mToast = Toast.makeText(
                AppManager.endOfStack(),
                "",
                Toast.LENGTH_SHORT
            )
            mToast.setText(msg)
            mToast.show()
        }

        /**
         * 居中屏幕显示
         * @param msg 内容
         */
        fun center(msg: String) {
            mToast = Toast.makeText(
                AppManager.endOfStack(),
                "",
                Toast.LENGTH_SHORT
            )
            mToast.setText(msg)
            mToast.setGravity(Gravity.CENTER, 0, 0)
            mToast.show()
        }

    }
}