package com.jadyn.ai.kotlind.utils

import android.graphics.Color
import android.text.*
import android.text.style.ImageSpan
import android.text.style.MetricAffectingSpan
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jadyn.ai.kotlind.base.KD
import com.jadyn.ai.kotlind.function.ui.getResDrawable

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

fun String?.toIntOrDefault(def: Int = 0): Int {
    if (isNullOrBlank()) {
        return def
    }
    return try {
        Integer.parseInt(this!!)
    } catch (e: Exception) {
        def
    }
}

fun getS(@StringRes id: Int, defS: String = ""): String {
    if (id == -1) {
        return ""
    }
    return try {
        KD.applicationWrapper().getString(id)
    } catch (e: Exception) {
        defS
    }
}

fun getS(@StringRes id: Int, vararg formatArgs: Any): String {
    return try {
        KD.applicationWrapper().getString(id, *formatArgs)
    } catch (e: Exception) {
        ""
    }
}

/**
 * 给文本末端附带drawable
 * */
fun String.drawableEnd(@DrawableRes id: Int, padding: Float = 0f, size: android.util.Size? = null): CharSequence {
    val drawable = getResDrawable(id)
    drawable ?: return this
    val ps = dp2px(padding)
    drawable.setBounds(ps, 0, ps + (size?.width ?: drawable.intrinsicWidth), size?.height ?: drawable.intrinsicHeight)
    val imgSpan = ImageSpan(drawable)
    val sp = SpannableString("$this ")
    sp.setSpan(imgSpan, sp.length - 1, sp.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    sp.setSpan(CenterVerticalSpan(), 0, sp.length - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return sp
}

class CenterVerticalSpan : MetricAffectingSpan() {
    override fun updateMeasureState(textPaint: TextPaint) {
        textPaint.baselineShift += getBaselineShift(textPaint)
    }

    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.baselineShift += getBaselineShift(textPaint)
    }

    private fun getBaselineShift(tp: TextPaint): Int {
//        val total = tp.ascent() + tp.descent()
//        return (total / 2f).toInt()
        return -tp.descent().toInt()
    }
}