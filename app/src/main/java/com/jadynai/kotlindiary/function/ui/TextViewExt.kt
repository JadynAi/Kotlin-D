package com.jadynai.kotlindiary.function.ui

import android.R
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.TextView

/**
 *@version:
 *@FileDescription:TextView扩展函数
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
inline fun TextView.drawable(textDrawable: TextDrawable.() -> Unit) {
    val d = TextDrawable()
    d.textDrawable()
    this.setCompoundDrawablesWithIntrinsicBounds(d.dl, d.dt, d.dr, d.db)
}

class TextDrawable(var dl: Drawable? = null, var dt: Drawable? = null, var dr: Drawable? = null, var db: Drawable? = null) {

    fun drawLeft(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int) {
        dl = getPressDrawable(normalRes, selectRes)
    }

    fun drawTop(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int) {
        dt = getPressDrawable(normalRes, selectRes)
    }

    fun drawRight(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int) {
        dr = getPressDrawable(normalRes, selectRes)
    }

    fun drawBottom(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int) {
        db = getPressDrawable(normalRes, selectRes)
    }
}

inline fun TextView.pressTextColor(normalColor: Int, pressColor: Int) {
    val list = ColorStateList(arrayOf(intArrayOf(R.attr.state_pressed),
            intArrayOf()), intArrayOf(pressColor, normalColor))
    this.setTextColor(list)
}

inline fun TextView.selectTextColor(normalColor: Int, pressColor: Int) {
    val list = ColorStateList(arrayOf(intArrayOf(R.attr.state_selected),
            intArrayOf()), intArrayOf(pressColor, normalColor))
    this.setTextColor(list)
}

inline fun TextView.checkedTextColor(normalColor: Int, pressColor: Int) {
    val list = ColorStateList(arrayOf(intArrayOf(R.attr.state_checked),
            intArrayOf()), intArrayOf(pressColor, normalColor))
    this.setTextColor(list)
}
