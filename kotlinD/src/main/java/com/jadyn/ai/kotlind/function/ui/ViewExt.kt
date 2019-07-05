package com.jadyn.ai.kotlind.function.ui

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.DrawableRes
import android.support.constraint.ConstraintLayout
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import com.jadyn.ai.kotlind.utils.dip2px

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

fun View.setHeightWithW(wRatio: Float, hRatio: Float) {
    val params = layoutParams
    if (params is ConstraintLayout.LayoutParams) {
        params.dimensionRatio = "W : $wRatio:$hRatio"
        layoutParams = params
    }
}

fun View.setWidthWithH(wRatio: Float, hRatio: Float) {
    val params = layoutParams
    if (params is ConstraintLayout.LayoutParams) {
        params.dimensionRatio = "H : $wRatio:$hRatio"
        layoutParams = params
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
        roundInternal(height * 0.5f, solidColor, strokeW, strokeColor)
    }
}

/**
 * 四个角，r的size必须为4
 * */
fun View.roundArray(r: FloatArray, solidColor: Int = Color.WHITE, strokeW: Float = 0f, strokeColor: Int = Color.TRANSPARENT) {
    roundInternalArray(r.map {
        dip2px(it).toFloat()
    }.toFloatArray(), solidColor, strokeW, strokeColor)
}

fun View.round(r: Float = 2f, solidColor: Int = Color.WHITE, strokeW: Float = 0f, strokeColor: Int = Color.TRANSPARENT) {
    roundInternal(dip2px(r).toFloat(), solidColor, strokeW, strokeColor)
}

fun View.roundInternal(r: Float = dip2px(2f).toFloat(), solidColor: Int = Color.WHITE, strokeW: Float = 0f, strokeColor: Int = Color.TRANSPARENT) {
    val roundDrawable = RoundDrawable(r, solidColor, strokeW, strokeColor)
    this.background = roundDrawable.build()
    //避免子View影响到背景
    this.clipToOutline = true
}

fun View.roundInternalArray(r: FloatArray, solidColor: Int = Color.WHITE, strokeW: Float = 0f, strokeColor: Int = Color.TRANSPARENT) {
    val roundDrawable = RoundDrawable(r, solidColor, strokeW, strokeColor)
    this.background = roundDrawable.build()
    //避免子View影响到背景
    this.clipToOutline = true
}

class RoundDrawable(private val rArray: FloatArray,
                    private var solidColor: Int = Color.WHITE,
                    private val strokeW: Float = 0f,
                    private val strokeColor: Int = Color.TRANSPARENT) {

    constructor(r: Float = dip2px(2f).toFloat(),
                solidColor: Int = Color.WHITE, strokeW: Float = 0f,
                strokeColor: Int = Color.TRANSPARENT) : this(floatArrayOf(r, r, r, r), solidColor,
            strokeW, strokeColor)

    init {
        if (rArray.size != 4) {
            throw IllegalArgumentException("round corner size must is 4!!!")
        }
    }

    fun build(): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.setColor(this.solidColor)
        gradientDrawable.setStroke(dip2px(strokeW), strokeColor)
        gradientDrawable.cornerRadii = floatArrayOf(
                rArray[0], rArray[0],
                rArray[1], rArray[1],
                rArray[2], rArray[2],
                rArray[3], rArray[3]
        )
        return gradientDrawable
    }
}

fun pressColorAll(normalColor: Int, pressColor: Int, vararg views: View) {
    if (views.isEmpty()) {
        return
    }
    views.forEach {
        it.pressColor(normalColor, pressColor)
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

//-----checkBox之类的设置button的drawable
fun CompoundButton.checkedButton(normal: Drawable, press: Drawable, drawableHandle: (StateListDrawable) -> Unit = {}) {
    val checkedDrawable = getCheckedDrawable(normal, press)
    drawableHandle.invoke(checkedDrawable)
    this.buttonDrawable = checkedDrawable
}

fun CompoundButton.checkedButton(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int,
                                 drawableHandle: (StateListDrawable) -> Unit = {}) {
    val checkedDrawable = getCheckedDrawable(normalRes, pressRes)
    drawableHandle.invoke(checkedDrawable)
    this.buttonDrawable = checkedDrawable
}

fun CompoundButton.checkedColorButton(normalColor: Int, checkedColor: Int,
                                      drawableHandle: (StateListDrawable) -> Unit = {}) {
    val checkedDrawable = getCheckedDrawable(ColorDrawable(normalColor), ColorDrawable(checkedColor))
    drawableHandle.invoke(checkedDrawable)
    this.buttonDrawable = checkedDrawable
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

fun View.selected(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    this.background = getSelectedDrawable(normalRes, pressRes)
}

fun View.selected(normal: Drawable, press: Drawable) {
    this.background = getSelectedDrawable(normal, press)
}

fun View.selectedColor(normalColor: Int, checkedColor: Int) {
    this.background = getSelectedDrawable(ColorDrawable(normalColor), ColorDrawable(checkedColor))
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

fun clearViewStatus(v: View?) {
    v?.apply {
        setAlpha(1f)
        setScaleY(1f)
        setScaleX(1f)
        setTranslationY(0f)
        setTranslationX(0f)
        setRotation(0f)
        setRotationY(0f)
        setRotationX(0f)
        setPivotY((v.measuredHeight / 2).toFloat())
        setPivotX((v.measuredWidth / 2).toFloat())
        v.animate().setInterpolator(null).startDelay = 0
    }
}

fun View.hideSoftKeyboard() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun measureText(text: String, width: Int, textPaint: TextPaint): IntArray {
    val ints = IntArray(2)
    if (text.isNullOrBlank()) {
        ints[0] = 0
        ints[1] = 0
        return ints
    }
    val staticLayout = StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true)
    ints[0] = width
    ints[1] = staticLayout.height
    return ints
}

/**
 * @param text      测量的文字
 * @param textPaint 绘制文字的画笔
 * @return 特定画笔画出的文字的宽高
 */
fun measureText(text: String, textPaint: TextPaint): IntArray {
    val ints = IntArray(2)
    if (text.isNullOrBlank()) {
        ints[0] = 0
        ints[1] = 0
        return ints
    }
    val arr = text.trim { it <= ' ' }.split("\n".toRegex())
            .dropLastWhile { it.isEmpty() }.toTypedArray()
    var maxLength = 0f
    for (s in arr) {
        maxLength = Math.max(maxLength, textPaint.measureText(s))
    }
    val width = (maxLength + 2.5f).toInt()
    val staticLayout = StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL,
            1f, 0f, true)
    ints[0] = width
    ints[1] = staticLayout.height
    return ints
}
//
//fun TabLayout.choose(index: Int) {
//    if (index < 0 || index >= this.tabCount) {
//        return
//    }
//    try {
//        val clazz = this.javaClass
//        val method = clazz.getDeclaredMethod("selectTab", TabLayout.Tab::class.java)
//        method.isAccessible = true
//        method.invoke(this, this.getTabAt(index))
//    } catch (e: Exception) {
//        Logger.t("choose").d("error $e")
//    }
//}
