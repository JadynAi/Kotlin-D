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
fun getSelectDrawable(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int): StateListDrawable {
    val sd = StateListDrawable()
    sd.addState(intArrayOf(R.attr.state_selected), getDrawable(selectRes))
    sd.addState(intArrayOf(), getDrawable(normalRes))
    return sd
}

fun getPressDrawable(normalRes: String, pressColor: String): StateListDrawable {
    return getPressDrawable(ColorDrawable(parseColor(normalRes)), ColorDrawable(parseColor(pressColor)))
}

fun getPressDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getPressDrawable(getDrawable(normalRes), getDrawable(pressRes))
}

fun getPressDrawable(nor: Drawable?, press: Drawable?): StateListDrawable {
    val sd = StateListDrawable()
    press?.apply {
        sd.addState(intArrayOf(R.attr.state_pressed), this)
    }
    nor?.apply {
        sd.addState(intArrayOf(), this)
    }
    return sd
}
