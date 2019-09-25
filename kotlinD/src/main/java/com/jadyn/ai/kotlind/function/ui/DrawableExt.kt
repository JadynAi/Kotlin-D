package com.jadyn.ai.kotlind.function.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.*
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.jadyn.ai.kotlind.base.KD
import com.jadyn.ai.kotlind.utils.dp2px
import com.jadyn.ai.kotlind.utils.parseColor

/**
 *@version:
 *@FileDescription: Drawable相关代码
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
fun getPressDrawable(normalRes: String, pressColor: String): StateListDrawable {
    return getPressDrawable(ColorDrawable(parseColor(normalRes)), ColorDrawable(parseColor(pressColor)))
}

fun getPressDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getPressDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getPressDrawable(nor: Drawable?, press: Drawable?): StateListDrawable {
    val sd = StateListDrawable()
    press?.apply {
        sd.addState(intArrayOf(android.R.attr.state_pressed), this)
    }
    nor?.apply {
        sd.addState(intArrayOf(), this)
    }
    return sd
}

fun getCheckedDrawable(normalRes: String, checkedColor: String): StateListDrawable {
    return getCheckedDrawable(ColorDrawable(parseColor(normalRes)), ColorDrawable(parseColor(checkedColor)))
}

fun getCheckedDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getCheckedDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getCheckedDrawable(nor: Drawable?, checked: Drawable?): StateListDrawable {
    val sd = StateListDrawable()
    checked?.apply {
        sd.addState(intArrayOf(android.R.attr.state_checked), this)
    }
    nor?.apply {
        sd.addState(intArrayOf(), this)
    }
    return sd
}

fun getSelectedDrawable(normalRes: String, checkedColor: String): StateListDrawable {
    return getSelectedDrawable(ColorDrawable(parseColor(normalRes)), ColorDrawable(parseColor(checkedColor)))
}

fun getSelectedDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getSelectedDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getSelectedDrawable(nor: Drawable?, checked: Drawable?): StateListDrawable {
    val sd = StateListDrawable()
    checked?.apply {
        sd.addState(intArrayOf(android.R.attr.state_selected), this)
    }
    nor?.apply {
        sd.addState(intArrayOf(), this)
    }
    return sd
}

fun getLevelDrawable(@DrawableRes vararg ids: Int): Drawable {
    val list = ids.map {
        getResDrawable(it)!!
    }.toTypedArray()
    return getLevelDrawable(*list)
}

fun getLevelDrawable(vararg ds: Drawable): Drawable {
    val drawable = LevelListDrawable()
    ds.forEachIndexed { index, d ->
        val i = index + 1
        drawable.addLevel(i, i, d)
    }
    return drawable
}

fun roundDrawable(r: Float = dp2px(2f).toFloat(), solidColor: Int = Color.WHITE,
                  strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT): GradientDrawable {
    return roundDrawable(floatArrayOf(r, r, r, r), solidColor, strokeW, strokeColor)
}

fun roundDrawable(rArray: FloatArray, solidColor: Int = Color.WHITE, strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT): GradientDrawable {
    require(rArray.size == 4) { "round corner size must is 4!!!" }
    val gradientDrawable = GradientDrawable()
    gradientDrawable.mutate()
    gradientDrawable.shape = GradientDrawable.RECTANGLE
    gradientDrawable.setColor(solidColor)
    gradientDrawable.setStroke(dp2px(strokeW), strokeColor)
    if (rArray[0] == rArray[1] && rArray[2] == rArray[3] && rArray[1] == rArray[2]) {
        gradientDrawable.cornerRadius = rArray[0]
    } else {
        gradientDrawable.cornerRadii = floatArrayOf(
                rArray[0], rArray[0],
                rArray[1], rArray[1],
                rArray[2], rArray[2],
                rArray[3], rArray[3]
        )
    }
    return gradientDrawable
}

fun ovalDrawable(solidColor: Int, w: Int, h: Int): GradientDrawable {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.OVAL
    drawable.setSize(w, h)
    drawable.setColor(solidColor)
    return drawable
}

fun getResDrawable(resId: Int, context: Context? = null): Drawable? {
    return try {
        ContextCompat.getDrawable(context ?: KD.applicationWrapper(), resId)
    } catch (e: Exception) {
        null
    }
}
