package com.jadynai.kotlindiary.function.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View
import com.jadynai.kotlindiary.base.BaseApplication

/**
 *@version:
 *@FileDescription: View相关
 *@Author:jing
 *@Since:2018/6/30
 *@ChangeList:
 */
inline fun dip2px(dpValue: Float): Int {
    val scale = BaseApplication.instance.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

inline fun getDrawable(resId: Int): Drawable {
    return ContextCompat.getDrawable(BaseApplication.instance, resId)!!
}

fun View.setViewVisible(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.click(click: (View) -> Unit) {
    this.setOnClickListener(click)
}

//View根据高计算宽
fun View.computeWidthWithH(ratio: Float) {
    this.post {
        val params = this.layoutParams
        params.width = (this.height * ratio).toInt()
        this.layoutParams = params
    }
}

//View根据宽计算高
fun View.computeHeightWithW(ratio: Float) {
    this.post {
        val params = this.layoutParams
        params.height = (this.width * ratio).toInt()
        this.layoutParams = params
    }
}

/*
* View设置圆角矩形背景，默认2dp，白色solid
* */
fun View.round(drawable: RoundDrawable.() -> Unit) {
    val roundDrawable = RoundDrawable()
    roundDrawable.drawable()
    this.background = roundDrawable.build()
}

class RoundDrawable {
    private var cornerRadius = dip2px(2f)
    private var solidColor: Int = Color.WHITE

    fun cornerR(r: Float) {
        cornerRadius = dip2px(r)
    }

    fun solid(color: Int) {
        solidColor = color
    }

    fun build(): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(this.solidColor)
        gradientDrawable.cornerRadius = cornerRadius.toFloat()
        return gradientDrawable
    }
}

inline fun View.press(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    this.background = getPressDrawable(normalRes, pressRes)
}

inline fun View.pressColor(normalColor: Int, pressColor: Int) {
    this.background = getPressDrawable(ColorDrawable(pressColor), ColorDrawable(normalColor))
}
