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
    val sd = getSelectDrawable(selectRes, normalRes)
    this.setImageDrawable(sd)
    this.setOnClickListener {
        this.isSelected = !this.isSelected
        click(it)
    }
}

inline fun ImageView.pressSrc(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    val sd = getPressDrawable(normalRes, pressRes)
    this.setImageDrawable(sd)
}