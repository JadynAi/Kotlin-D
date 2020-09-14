package com.jadyn.ai.kotlind.function.ui

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
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
//------enable------
fun getEnableDrawable(normal: String, changed: String): StateListDrawable {
    return getEnableDrawable(ColorDrawable(parseColor(normal)), ColorDrawable(parseColor(changed)))
}

fun getEnableDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getEnableDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getEnableDrawable(nor: Drawable?, press: Drawable?): StateListDrawable {
    return getStateDrawable(nor, press, android.R.attr.state_enabled)
}

//------press------
fun getPressDrawable(nor: String, pressColor: String): StateListDrawable {
    return getPressDrawable(ColorDrawable(parseColor(nor)), ColorDrawable(parseColor(pressColor)))
}

fun getPressDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getPressDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getPressDrawable(nor: Drawable?, press: Drawable?): StateListDrawable {
    return getStateDrawable(nor, press, android.R.attr.state_pressed)
}

//------check------
fun getCheckedDrawable(normalRes: String, checkedColor: String): StateListDrawable {
    return getCheckedDrawable(ColorDrawable(parseColor(normalRes)), ColorDrawable(parseColor(checkedColor)))
}

fun getCheckedDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getCheckedDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getCheckedDrawable(nor: Drawable?, checked: Drawable?): StateListDrawable {
    return getStateDrawable(nor, checked, android.R.attr.state_checked)
}

//------select------
fun getSelectedDrawable(nor: String, checkedColor: String): StateListDrawable {
    return getSelectedDrawable(ColorDrawable(parseColor(nor)), ColorDrawable(parseColor(checkedColor)))
}

fun getSelectedDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getSelectedDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getSelectedDrawable(nor: Drawable?, checked: Drawable?): StateListDrawable {
    return getStateDrawable(nor, checked, android.R.attr.state_selected)
}

fun getStateDrawable(nor: Drawable?, checked: Drawable?, @AttrRes state: Int): StateListDrawable {
    val sd = StateListDrawable()
    checked?.apply {
        sd.addState(intArrayOf(state), this)
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
    return roundDrawable(floatArrayOf(r, r, r, r), intArrayOf(solidColor), strokeW, strokeColor)
}

fun roundDrawable(r: Float = dp2px(2f).toFloat(),
                  solidColors: IntArray = intArrayOf(Color.WHITE),
                  orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
                  strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT): GradientDrawable {
    return roundDrawable(floatArrayOf(r, r, r, r), solidColors, strokeW, strokeColor, orientation)
}

fun roundDrawable(rArray: FloatArray, solidColor: Int = Color.WHITE, strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT): GradientDrawable {
    return roundDrawable(rArray, intArrayOf(solidColor), strokeW, strokeColor)
}

fun roundDrawable(rArray: FloatArray, solidColors: IntArray = intArrayOf(Color.WHITE), strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT,
                  orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM): GradientDrawable {
    require(rArray.size == 4) { "round corner size must is 4!!!" }
    val gradientDrawable = GradientDrawable()
//    gradientDrawable.mutate()
    gradientDrawable.shape = GradientDrawable.RECTANGLE
    if (solidColors.size > 1) {
        gradientDrawable.orientation = orientation
        gradientDrawable.colors = solidColors
    } else {
        gradientDrawable.setColor(solidColors.first())
    }
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

fun ovalDrawable(solidColor: Int, strokeW: Float = 0f,
                 strokeColor: Int = Color.TRANSPARENT, w: Int = 0, h: Int = 0): GradientDrawable {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.OVAL
    if (w != 0 && h != 0) {
        drawable.setSize(w, h)
    }
    drawable.setColor(solidColor)
    drawable.setStroke(dp2px(strokeW), strokeColor)
    return drawable
}

fun getResDrawable(resId: Int, context: Context? = null): Drawable? {
    return try {
        ContextCompat.getDrawable(context ?: KD.applicationWrapper(), resId)
    } catch (e: Exception) {
        null
    }
}

fun Context.getResDrawable(resId: Int): Drawable? {
    return getResDrawable(resId, this)
}

fun Drawable.tintSelected(@ColorInt nor: Int, @ColorInt state: Int): Drawable {
    return tintState(nor, state, R.attr.state_selected)
}

fun Drawable.tintEnable(@ColorInt nor: Int, @ColorInt state: Int): Drawable {
    return tintState(nor, state, R.attr.state_enabled)
}

fun Drawable.tintChecked(@ColorInt nor: Int, @ColorInt state: Int): Drawable {
    return tintState(nor, state, android.R.attr.state_checked)
}

/**
 * ColorStateList 两个参数，第一参数就是状态数组。第二个参数为状态对应的颜色
 *
 * @param nor state false状态下的颜色
 * @param state State触发时，为true的颜色
 * @param stateId
 * */
fun Drawable.tintState(@ColorInt nor: Int, @ColorInt state: Int, @AttrRes stateId: Int): Drawable {
    setTintList(ColorStateList(arrayOf(intArrayOf(stateId), intArrayOf()),
            intArrayOf(state, nor)))
    mutate()
    return this
}
