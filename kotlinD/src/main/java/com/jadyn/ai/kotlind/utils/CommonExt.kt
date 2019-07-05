package com.jadyn.ai.kotlind.utils

import android.content.res.Resources
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.jadyn.ai.kotlind.base.BaseApplication

/**
 *@version:
 *@FileDescription: 通用工具文件
 *@Author:jing
 *@Since:2018/6/20
 *@ChangeList:
 */

private val MIN_WVGA_HEIGHT = 700
private val WVGA_HEIGHT = 800
private val MIN_HD_HEIGHT = 1180
private val HD_HEIGHT = 1280

fun resources(): Resources = BaseApplication.instance.resources

val phonePixels: IntArray
    get() {
        val metrics = resources().displayMetrics
        val curWidth = metrics.widthPixels
        var curHeight = metrics.heightPixels
        if (curHeight in MIN_WVGA_HEIGHT..WVGA_HEIGHT) {
            curHeight = WVGA_HEIGHT
        }
        if (curHeight in MIN_HD_HEIGHT..HD_HEIGHT) {
            curHeight = HD_HEIGHT
        }
        return intArrayOf(curWidth, curHeight)
    }

fun dip2px(dpValue: Float): Int {
    val scale = resources().displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun toastS(string: String) {
    Toast.makeText(BaseApplication.instance, string, Toast.LENGTH_SHORT).show()
}

fun getResColor(@ColorRes id: Int) = ContextCompat.getColor(BaseApplication.instance, id)

fun getResDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(BaseApplication.instance, id)

fun getS(@StringRes id: Int, defS: String = ""): String {
    return try {
        BaseApplication.instance.getString(id)
    } catch (e: Exception) {
        defS
    }
}

fun getS(@StringRes id: Int, vararg formatArgs: Any): String {
    return try {
        BaseApplication.instance.getString(id, *formatArgs)
    } catch (e: Exception) {
        ""
    }
}

fun parseInt(s: String?, def: Int = 0): Int {
    return try {
        Integer.parseInt(s)
    } catch (e: Exception) {
        def
    }
}

fun computeDisWithScreenW(denominator: Int, member: Int): Int {
    val ratio = denominator.toFloat() / member.toFloat()
    return (phonePixels[0] / ratio + 0.5f).toInt()
}

fun computeDisWithPhoneHeight(denominator: Int, member: Int): Int {
    val ratio = denominator.toFloat() / member.toFloat()
    return (phonePixels[1] / ratio + 0.5f).toInt()
}