package com.jadyn.ai.kotlind.function.ui

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView

/**
 *@version:
 *@FileDescription:ImageView扩展函数
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
fun ImageView.clickSelect(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int, click: (Boolean) -> Unit= {}) {
    select(normalRes, selectRes)
    this.setOnClickListener {
        this.isSelected = !this.isSelected
        click(isSelected)
    }
}

fun ImageView.pressSrc(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    val sd = getPressDrawable(normalRes, pressRes)
    setImageDrawable(sd)
}

fun ImageView.select(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int) {
    val sd = getSelectedDrawable(normalRes, selectRes)
    setImageDrawable(sd)
}

fun ImageView.select(normal: Drawable, press: Drawable) {
    val sd = getSelectedDrawable(normal, press)
    setImageDrawable(sd)
}