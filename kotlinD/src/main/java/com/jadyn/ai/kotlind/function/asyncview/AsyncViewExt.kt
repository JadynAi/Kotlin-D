package com.jadyn.ai.kotlind.function.asyncview

import android.os.Looper

/**
 *JadynAi since 2021/4/29
 */

/**
 * 把当前线程的looper强行设置为mainLooper
 * */
fun tryForceSetCurThreadMainLooper():Boolean {
    return try {
        val field = Looper::class.java.getDeclaredField("sThreadLocal")
        field.isAccessible = true
        val get = field.get(Looper.getMainLooper())
        @Suppress("UNCHECKED_CAST") val threadLocal = get as? ThreadLocal<Looper>
        threadLocal?.set(Looper.getMainLooper())
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}