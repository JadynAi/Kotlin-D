package com.jadyn.ai.kotlind.utils

import android.graphics.Color
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.jadyn.ai.kotlind.base.KD

/**
 *@version:
 *@FileDescription: 字符串解析类
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
fun parseColor(color: String): Int {
    return parseColor(color, Color.BLACK)
}

fun parseColor(colorS: String, @ColorInt defColor: Int): Int {
    var newColor = colorS
    if (colorS.toLowerCase().startsWith("0x")) {
        newColor = newColor.replace("0x", "#")
    }
    return try {
        Color.parseColor(newColor)
    } catch (e: Exception) {
        defColor
    }
}

fun String?.getReal(def: String = ""): String {
    if (isNullOrBlank()) {
        return def
    }
    return this!!
}

/*
* 测量文字宽高
* */
fun measureText(text: String?, textPaint: TextPaint): IntArray {
    val ints = IntArray(2)
    if (text.isNullOrBlank()) {
        ints[0] = 0
        ints[1] = 0
        return ints
    }
    val arr = text!!.trim { it <= ' ' }.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    var maxLength = 0f
    for (s in arr) {
        maxLength = Math.max(maxLength, textPaint.measureText(s))
    }
    val width = (maxLength + 2.5f).toInt()
    val staticLayout = StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true)
    ints[0] = width
    ints[1] = staticLayout.height
    return ints
}

fun getS(@StringRes id: Int, defS: String = ""): String {
    return try {
        KD.application.getString(id)
    } catch (e: Exception) {
        defS
    }
}

fun getS(@StringRes id: Int, vararg formatArgs: Any): String {
    return try {
        KD.application.getString(id, *formatArgs)
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