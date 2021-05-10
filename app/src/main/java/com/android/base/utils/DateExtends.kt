package com.android.base.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

/**
 * 日期处理拓展
 */

private val weekDays by lazy {
    arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
}

enum class DATETIME {
    DAY,
    HOUR,
    MINUTE,
    SS
}


/**
 * 获取某年某月有多少天
 * @param year 年份
 * @param month 月份
 */
fun Calendar.getDayOfMonth(year: Int, month: Int): Int {
    set(year, month, 0) //输入类型为int类型
    return get(Calendar.DAY_OF_MONTH)
}

/**
 * 获取当前日期所在周的天
 */
fun Calendar.getWeekOfNow() :MutableList<String>{
    val list = ArrayList<String>()
    val start = this.firstDayOfWeek
    val end = this.getMaximum(Calendar.DAY_OF_WEEK)
    for (i in start..end) {
        this.set(Calendar.DAY_OF_WEEK, i)
        val str = time.toString()

        val dataStr = if (str.subSequence(8, 9) == "0")
            str.substring(9, 10)
        else
            str.substring(8, 10)
        list.add(dataStr)
    }
    return list
}


/**
 * 判断当前是周几
 *
 * @param dt 日期（起码包含年月日）
 *
 */
fun Calendar.getWeekOfDate(dt: Date): String {
    time = dt
    var w = get(Calendar.DAY_OF_WEEK) - 1
    if (w < 0)
        w = 0
    return weekDays[w]
}

/**
 *获取当前月第几天
 */
fun Calendar.day(): Int {
    return get(Calendar.DAY_OF_MONTH)
}

/**
 * 获取现在是当天的第几小时
 */
fun Calendar.hour(): Int {
    return get(Calendar.HOUR_OF_DAY)
}

/**
 * 查找临近几天日期,GregorianCalendar()用此类调用
 */
fun Calendar.findDay(between: Int): Date {
    time = Date()
    add(Calendar.DATE, between)
    return time
}

/**
 * 获取当前秒数
 */
fun Calendar.second(): Int {
    return get(Calendar.SECOND)
}

/**
 * 获取当前分针数
 */
fun Calendar.minute(): Int {
    return get(Calendar.MINUTE)
}

/**
 * 年份
 */
fun Calendar.year(): Int {
    return get(Calendar.YEAR)
}

/**
 * 月份
 */
fun Calendar.month(): Int {
    return get(Calendar.MONTH) + 1
}

/**
 * 获取当日0整点
 */
fun Calendar.zeroDate(): Long {
    return this.timeInMillis - (this.timeInMillis + TimeZone.getDefault().rawOffset) % (24 * 60 * 60 * 1000)
}


/**
 * 时间戳获取当日0整点
 */
fun Long.zeroDate(): Long {
    return this - (this + TimeZone.getDefault().rawOffset) % (24 * 60 * 60 * 1000)
}


/**
 *计算时间戳的差值
 */
fun Long.between(cal2: Long, typeCode: DATETIME, isPhp: Boolean = false): Long {
    var res = abs(if (isPhp) this * 1000 else this - cal2)
    res /= when (typeCode) {
        DATETIME.DAY -> (24 * 60 * 60 * 1000)
        DATETIME.MINUTE -> (60 * 1000)
        DATETIME.SS -> 1000
        DATETIME.HOUR -> (60 * 60 * 1000)
    }
    return res
}

/**
 *计算两个日期的差值
 */
fun Calendar.between(cal2: Calendar, typeCode: DATETIME): Long {
    var res = abs(timeInMillis - cal2.timeInMillis)
    res /= when (typeCode) {
        DATETIME.DAY -> (24 * 60 * 60 * 1000)
        DATETIME.MINUTE -> (60 * 1000)
        DATETIME.SS -> 1000
        DATETIME.HOUR -> (60 * 60 * 1000)
    }
    return res
}

/**
 * 日期字符串转时间戳
 */
fun String.toLongDate(timeStyle: String): Long {
    val sdf = SimpleDateFormat(timeStyle, Locale.CHINA)
    val cal = Calendar.getInstance()
    try {
        val date = sdf.parse(this)
        if (date != null)
            cal.time = date
    } catch (e: ParseException) {
        return 0L
    }

    return cal.timeInMillis
}

/**
 * 时间戳转样式字符串
 */
fun Long.toDate(timeStyle: String, isPhp: Boolean = false): String {
    val d = Date(if (isPhp) this * 1000 else this)
    val sdf = SimpleDateFormat(timeStyle, Locale.CHINA)
    return sdf.format(d)
}

/**
 * 格式转化
 */
fun String.dateToDate(timeStyle: String, timeStyle_now: String): String {
    val sdf = SimpleDateFormat(timeStyle, Locale.CHINA)
    val sdf1 = SimpleDateFormat(timeStyle_now, Locale.CHINA)
    try {
        val date = sdf.parse(this)
        return sdf1.format(date!!)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return this
}


