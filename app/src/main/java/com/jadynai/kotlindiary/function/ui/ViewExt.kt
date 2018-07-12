package com.jadynai.kotlindiary.function.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.jadynai.kotlindiary.base.BaseApplication

/**
 *@version:
 *@FileDescription: View相关
 *@Author:jing
 *@Since:2018/6/30
 *@ChangeList:
 */
fun dip2px(dpValue: Float): Int {
    val scale = BaseApplication.instance.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun getDrawable(resId: Int): Drawable? {
    return try {
        ContextCompat.getDrawable(BaseApplication.instance, resId)
    } catch (e: Exception) {
        null
    }
}

fun View.setVisible(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.click(click: (View) -> Unit) {
    this.setOnClickListener(click)
}

//多View同一个点击事件设定
fun doClick(click: (View) -> Unit, vararg views: View) {
    for (view in views) {
        view.click {
            click(it)
        }
    }
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
fun View.round(r: Float = 2f, color: Int = Color.WHITE) {
    val roundDrawable = RoundDrawable(r, color)
    this.background = roundDrawable.build()
}

class RoundDrawable(r: Float = 2f, private var solidColor: Int = Color.WHITE) {

    private var cornerRadius = dip2px(r)

    fun build(): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(this.solidColor)
        gradientDrawable.cornerRadius = cornerRadius.toFloat()
        return gradientDrawable
    }
}

fun View.press(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    this.background = getPressDrawable(normalRes, pressRes)
}

fun View.pressColor(normalColor: Int, pressColor: Int) {
    this.background = getPressDrawable(ColorDrawable(pressColor), ColorDrawable(normalColor))
}

fun View.event(click: ((View) -> Unit)? = null, doubleTap: (() -> Unit)? = null,
               longPress: (() -> Unit)? = null) {
    this.isLongClickable = true
    val gestureDetector = GestureDetector(this.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            Log.d("event", "onSingleTapConfirmed ")
            click?.apply { this(this@event) }
            return false
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            Log.d("event", "onDoubleTap ")
            doubleTap?.apply { this() }
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            Log.d("event", "onLongPress ")
            longPress?.apply { this() }
        }
    })

    this.setOnTouchListener { v, event ->
        gestureDetector.onTouchEvent(event)
    }
}

fun setVisible(visible: Boolean, vararg views: View) {
    for (view in views) {
        view.setVisible(visible)
    }
}
