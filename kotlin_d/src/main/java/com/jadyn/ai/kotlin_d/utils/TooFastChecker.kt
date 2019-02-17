package com.jadyn.ai.kotlin_d.utils

import android.os.SystemClock

/**
 * @version:
 * @FileDescription: 触发时间检查
 * @Author:jing
 * @Since:2017/9/14
 * @ChangeList:
 */
class TooFastChecker @JvmOverloads constructor(private val mDefaultMinTimeSpan: Int = 500) {

    private var mLastTime: Long = 0

    val isTooFast: Boolean
        get() = isTooFast(mDefaultMinTimeSpan)

    private val curTimestamp: Long
        get() = SystemClock.elapsedRealtime()

    fun isTooFast(minTimeSpan: Int): Boolean {
        val curTimestamp = curTimestamp
        return if (curTimestamp - mLastTime < minTimeSpan) {
            true
        } else {
            mLastTime = curTimestamp
            false
        }
    }

    fun startTime() {
        mLastTime = curTimestamp
    }

    fun cancel() {
        mLastTime = 0
    }

    fun cancelDelay(delay: Int) {
        mLastTime = curTimestamp - mDefaultMinTimeSpan + delay
    }
}
