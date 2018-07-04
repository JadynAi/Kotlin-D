package com.jadynai.kotlindiary.utils

import android.graphics.Color
import android.support.annotation.ColorInt

/**
 *@version:
 *@FileDescription: 字符串解析类
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
inline fun parseColor(fontColor: String): Int {
    return parseColor(fontColor, Color.BLACK)
}

inline fun parseColor(colorS: String, @ColorInt defColor: Int): Int {
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