package com.jadynai.kotlindiary.function.ui

import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView

/**
 *@version:
 *@FileDescription:ImageView扩展函数
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
inline fun ImageView.clickSelect(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int, noinline click: (View) -> Unit) {
    select(normalRes, selectRes)
    this.setOnClickListener {
        this.isSelected = !this.isSelected
        click(it)
    }
}

inline fun ImageView.pressSrc(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    val sd = getPressDrawable(normalRes, pressRes)
    setImageDrawable(sd)
}

fun ImageView.select(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int) {
    val sd = getSelectDrawable(normalRes, selectRes)
    setImageDrawable(sd)
}