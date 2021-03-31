package com.android.base.utils.other
import android.text.InputFilter
import android.text.Spanned


/**
 * 过滤器数字型,保留两位小数
 */
class NumberFilter : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence {
        if (source.isEmpty()) {
            return ""
        }
        val sb = StringBuilder()
        sb.append(dest.subSequence(0, dstart))
            .append(source)
            .append(dest.subSequence(dend, dest.length))

        if (sb.toString().endsWith(".")) {
            sb.append("0")
        }

        if (!Regex("^(([1-9]\\d*)|0)(\\.\\d{1,3})?$").matches(sb.toString())) {
            return ""
        }
        if (sb.toString().contains(".")) {
            //验证小数点精度，保证小数点后只能输入两位
            val index = sb.toString().indexOf(".")
            val length = sb.toString().trim { it <= ' ' }.length - index
            if (length > 3 && dstart > index) {
                return ""
            }
        }
        return source
    }


}