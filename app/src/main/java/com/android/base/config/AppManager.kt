package com.android.base.config

import android.app.Activity
import java.util.*

/**
 * 管理activity
 */
object AppManager {

    private var stack: Stack<Activity> = Stack()


    var isExit = false

    /**
     * 添加Activity到堆栈
     */
    fun add(act: Activity) {
        stack.add(act)
    }

    /**
     * 结束指定的Activity
     */
    fun remove(act: Activity) {
        stack.remove(act)
    }

    /**
     * 移除指定activity
     */
    fun remove(cls: Class<*>) {
        stack.filter { it.javaClass == cls }.forEach {
            it.finish()
        }
    }

    /**
     * 移除指定activity以外对象
     */
    fun removeOther(cls: Class<*>) {
        stack.filter {it.javaClass != cls}.forEach{it.finish()}
    }


    /**
     * 获取Activity数量
     */
    fun size(): Int {
        return stack.size
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun endOfStack(): Activity {
        return stack.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finish() {
        stack.lastElement().finish()
        stack.remove(stack.lastElement())
    }

    /**
     * 结束所有Activity
     */
    fun close() {
        for (act: Activity in stack) {
            act.finish()
        }
        stack.clear()
    }


}