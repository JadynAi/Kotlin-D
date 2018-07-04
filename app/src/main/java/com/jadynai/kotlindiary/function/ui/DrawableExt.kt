package com.jadynai.kotlindiary.function.ui

import android.R
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.DrawableRes
import com.jadynai.kotlindiary.utils.parseColor

/**
 *@version:
 *@FileDescription: Drawable相关代码
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
inline fun getSelectDrawable(@DrawableRes selectRes: Int, @DrawableRes normalRes: Int): StateListDrawable {
    val sd = StateListDrawable()
    sd.addState(intArrayOf(R.attr.state_selected), getDrawable(selectRes))
    sd.addState(intArrayOf(), getDrawable(normalRes))
    return sd
}

inline fun getPressDrawable(normalRes: String, pressColor: String): StateListDrawable {
    return getPressDrawable(ColorDrawable(parseColor(normalRes)), ColorDrawable(parseColor(pressColor)))
}

inline fun getPressDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getPressDrawable(getDrawable(normalRes), getDrawable(pressRes))
}

inline fun getPressDrawable(nor: Drawable, press: Drawable): StateListDrawable {
    val sd = StateListDrawable()
    sd.addState(intArrayOf(R.attr.state_pressed), press)
    sd.addState(intArrayOf(), nor)
    return sd
}
