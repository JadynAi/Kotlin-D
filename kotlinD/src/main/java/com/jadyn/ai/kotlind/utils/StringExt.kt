package com.jadyn.ai.kotlind.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.*
import android.text.style.ImageSpan
import android.text.style.MetricAffectingSpan
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jadyn.ai.kotlind.base.KD
import com.jadyn.ai.kotlind.function.ui.getResDrawable
import kotlin.math.abs

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
    return drawableEnd(getResDrawable(id), padding, size)
}

fun String.drawableEnd(d: Drawable?, padding: Float = 0f, size: android.util.Size? = null): CharSequence {
    d ?: return this
    val ps = dp2px(padding)
    d.setBounds(ps, 0, ps + (size?.width ?: d.intrinsicWidth), size?.height ?: d.intrinsicHeight)
    val imgSpan = ImageSpan(d)
    val sp = SpannableString("$this ")
    sp.setSpan(imgSpan, sp.length - 1, sp.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    sp.setSpan(CenterVerticalSpan(), 0, sp.length - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return sp
}

fun String.drawableTop(@DrawableRes id: Int, padding: Float = 0f, size: android.util.Size? = null): CharSequence {
    val bitmap = BitmapFactory.decodeResource(KD.applicationWrapper().resources, id)
    bitmap ?: return this
    val ps = dp2px(padding)
    val finalB = if (size != null) Bitmap.createScaledBitmap(bitmap, size.width, size.height, true)
    else bitmap
    val createBitmap = if (padding == 0f) finalB else {
        val c = Bitmap.createBitmap(finalB.width, finalB.height + ps, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(c)
        canvas.drawColor(Color.TRANSPARENT)
        canvas.drawBitmap(finalB, 0f, 0f, null)
        c
    }
    val imgSpan = ImageSpan(KD.applicationWrapper(), createBitmap)
    val sp = SpannableString("c\n$this")
    sp.setSpan(imgSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    sp.setSpan(CenterVerticalSpan(), 1, sp.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
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
        val total = abs(tp.fontMetrics.top) + abs(tp.fontMetrics.bottom)
        return -(total / 2f - tp.fontMetrics.bottom).toInt()
    }
}