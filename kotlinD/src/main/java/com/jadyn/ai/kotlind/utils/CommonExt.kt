package com.jadyn.ai.kotlind.utils

import android.content.res.Resources
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.jadyn.ai.kotlind.base.KD

/**
 *@version:
 *@FileDescription: 通用工具文件
 *@Author:jing
 *@Since:2018/6/20
 *@ChangeList:
 */

fun resources(): Resources = KD.applicationWrapper().resources

val phonePixels: IntArray
    get() {
        val metrics = resources().displayMetrics
        return intArrayOf(metrics.widthPixels, metrics.heightPixels)
    }

val screenWidth by lazy {
    phonePixels[0]
}

val screenHeight by lazy {
    phonePixels[1]
}

fun dp2px(dpValue: Float): Int {
    val scale = resources().displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun toastS(string: String) {
    Toast.makeText(KD.applicationWrapper(), string, Toast.LENGTH_SHORT).show()
}

fun getResColor(@ColorRes id: Int) = ContextCompat.getColor(KD.applicationWrapper(), id)

fun computeDisWithScreenW(denominator: Int, member: Int): Int {
    val ratio = denominator.toFloat() / member.toFloat()
    return (phonePixels[0] / ratio + 0.5f).toInt()
}

fun computeDisWithPhoneHeight(denominator: Int, member: Int): Int {
    val ratio = denominator.toFloat() / member.toFloat()
    return (phonePixels[1] / ratio + 0.5f).toInt()
}

fun repeatTimeOut(time: Long, action: () -> Boolean, callback: () -> Unit) {
    val start = System.currentTimeMillis()
    while (action.invoke() && System.currentTimeMillis() - start <= time) {
        
    }
    callback.invoke()
}