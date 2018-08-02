package com.jadynai.kotlindiary.function.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.support.annotation.DrawableRes
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.jadynai.kotlindiary.utils.dip2px
import com.jadynai.kotlindiary.utils.getResDrawable

/**
 *@version:
 *@FileDescription: View相关
 *@Author:jing
 *@Since:2018/6/30
 *@ChangeList:
 */
fun getResDrawable(resId: Int): Drawable? {
    return try {
        getResDrawable(resId)
    } catch (e: Exception) {
        null
    }
}

fun View.setVisible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.toggleVisible() {
    visibility == if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

fun View.click(click: (View) -> Unit) {
    this.setOnClickListener(click)
}

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

fun View.updateWH(width: Int = layoutParams.width, height: Int = layoutParams.height) {
    val params = layoutParams
    params.width = width
    params.height = height
    layoutParams = params
}

/*
* View设置以高度为准的圆角矩形
* */
fun View.roundHeight(solidColor: Int = Color.WHITE, strokeW: Float = 0f, strokeColor: Int = Color.TRANSPARENT) {
    post {
        round(height * 0.5f, solidColor, strokeW, strokeColor)
    }
}

fun View.round(r: Float = 2f, solidColor: Int = Color.WHITE, strokeW: Float = 0f, strokeColor: Int = Color.TRANSPARENT) {
    val roundDrawable = RoundDrawable(r, solidColor, strokeW, strokeColor)
    this.background = roundDrawable.build()
}

class RoundDrawable(r: Float = 2f,
                    private var solidColor: Int = Color.WHITE,
                    private val strokeW: Float = 0f,
                    private val strokeColor: Int = Color.TRANSPARENT) {

    private var cornerRadius = dip2px(r)

    fun build(): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(this.solidColor)
        gradientDrawable.setStroke(dip2px(strokeW), strokeColor)
        gradientDrawable.cornerRadius = cornerRadius.toFloat()
        return gradientDrawable
    }
}

fun View.press(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    this.background = getPressDrawable(normalRes, pressRes)
}

fun View.press(normal: Drawable, press: Drawable) {
    this.background = getPressDrawable(normal, press)
}

fun View.pressColor(normalColor: Int, pressColor: Int) {
    this.background = getPressDrawable(ColorDrawable(normalColor), ColorDrawable(pressColor))
}

fun View.checked(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    this.background = getCheckedDrawable(normalRes, pressRes)
}

fun View.checked(normal: Drawable, press: Drawable) {
    this.background = getCheckedDrawable(normal, press)
}

fun View.checkedColor(normalColor: Int, checkedColor: Int) {
    this.background = getCheckedDrawable(ColorDrawable(normalColor), ColorDrawable(checkedColor))
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

fun ViewGroup.clipChild(isClip: Boolean) {
    clipToPadding = isClip
    clipChildren = false
}
