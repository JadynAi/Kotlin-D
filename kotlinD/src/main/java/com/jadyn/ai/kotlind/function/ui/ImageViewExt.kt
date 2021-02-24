package com.jadyn.ai.kotlind.function.ui

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.jadyn.ai.kotlind.R

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

fun ImageView.activated(normal: Drawable?, state: Drawable?) {
    if (normal == null || state == null) {
        return
    }
    val sd = getActivatedDrawable(normal, state)
    setImageDrawable(sd)
}

fun ImageView.tintEnable(@ColorInt normalColor: Int, @ColorInt stateColor: Int) {
    imageTintList = ColorStateList(arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf()),
            intArrayOf(stateColor, normalColor))
}