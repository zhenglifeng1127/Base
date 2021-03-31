package com.android.base.utils

import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * 常用简易算法扩展
 */

/**
 * 数字字符串转单位文本
 */
fun String?.numberText(append:String = ""):String{
    if(this.toString().isNumber()){
        val v = this.toString().toInt()
        return when {
            v <= 0 -> {
                return "${append}0"
            }
            v in 0..9999 -> {
                "${append}$v"
            }
            v in 10000..999999 -> {
                "${append}${String.format("%.2f", (v / 10000.0))}万"
            }
            v in 999999..9999999 -> {
                "${append}${String.format("%.2f", (v / 1000000.0))}百万"
            }
            v in 10000000..99999999 -> {
                return "${append}${String.format("%.2f", (v / 10000000.0))}千万"
            }
            else -> {
                "${append}${String.format("%.2f", (v / 100000000.0))}亿"
            }
        }
    }else{
        return "${append}0"
    }
}


/**
 * @param any(可以转化为数字的内容)乘法
 */
fun String.multiply(any: Any): Double {
    return BigDecimal(this).multiply(BigDecimal(any.toString())).toDouble()
}


/**
 * @param any(可以转化为数字的内容)乘法
 */
fun Double.multiply(any: Any): Double {
    return BigDecimal(this).multiply(BigDecimal(any.toString())).toDouble()
}


/**
 * @param any(可以转化为数字的内容)减法
 */
fun Double.subtract(any: Any): Double {
    return BigDecimal(this).subtract(BigDecimal(any.toString())).toDouble()
}

/**
 * @param any(可以转化为数字的内容)除法
 */
fun Double.divide(any: Any): Double {
    return BigDecimal(this).divide(BigDecimal(any.toString())).toDouble()
}

/**
 * @param any(可以转化为数字的内容)加法
 */
fun Double.add(any: Any): Double {
    return BigDecimal(this).add(BigDecimal(any.toString())).toDouble()
}

/**
 * 保留两位精度
 */
fun Double.number2f(): Double {
    return BigDecimal(this).setScale(2, BigDecimal.ROUND_HALF_UP).toDouble()
}

/**
 * 保留两位小数
 */
fun Float?.number2f(): String {
    return this.toString().format("%.2f", this)
}
/**
 * 保留两位小数,非四舍五入
 */
fun Double?.number2d():String{
    return this.toString().format("%.2d", this)
}

/**
 * 补0函数
 */
fun Int.format(style:String = "000"): String{
    val decimalFormat = DecimalFormat(style)
    return decimalFormat.format(this).nullString()
}

fun Double?.number2Zero(): String {
    val format = DecimalFormat("#.00")
    return format.format(this)
}
