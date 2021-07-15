package com.jadyn.ai.kotlind.function.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.viewpager.widget.ViewPager
import com.jadyn.ai.kotlind.utils.*

/**
 *@version:
 *@FileDescription: View相关
 *@Author:jing
 *@Since:2018/6/30
 *@ChangeList:
 */

fun View?.setVisible(show: Boolean) {
    this?.apply {
        visibility = if (show) View.VISIBLE else View.GONE
    }
}

fun View?.setVisibleOrInVisible(show: Boolean) {
    this?.apply {
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
}

fun View?.toggleVisible() {
    this?.apply {
        visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }
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
        params.height = height
        this.layoutParams = params
    }
}

//View根据宽计算高
fun View.computeHeightWithW(ratio: Float) {
    this.post {
        val params = this.layoutParams
        params.height = (this.width * ratio).toInt()
        params.width = width
        this.layoutParams = params
    }
}

inline fun <reified D : ViewGroup.LayoutParams> View.updateParams(c: (D) -> Unit) {
    layoutParams?.let {
        c.invoke(it as D)
        layoutParams = it
    }
}

fun View.updateWH(width: Int = layoutParams.width, height: Int = layoutParams.height) {
    val params = layoutParams
    params.width = width
    params.height = height
    layoutParams = params
}

/**
 * crossinline 间不能有return
 * */
inline fun View.forSureGetSize(crossinline callback: View.() -> Unit) {
    if (height <= 0) {
        if (visibility == View.GONE) {
            addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                    if (height > 0) {
                        callback()
                        removeOnLayoutChangeListener(this)
                    }
                }
            })
        } else {
            post {
                if (height > 0) {
                    callback()
                } else {
                    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                        override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                            if (height > 0) {
                                callback()
                                removeOnLayoutChangeListener(this)
                            }
                        }
                    })
                }
            }
        }
        return
    }
    callback()
}

/*
* View设置以高度为准的圆角矩形
* */
fun View.roundHeight(solidColor: Int = Color.WHITE, strokeW: Float = 0f,
                     strokeColor: Int = Color.TRANSPARENT) {
    forSureGetSize {
        roundInternal(height * 0.5f, solidColor, strokeW, strokeColor)
    }
}

fun View.roundHeightSelected(normalColor: Int, stateColor: Int) {
    forSureGetSize {
        roundInternal(height * 0.5f, normalColor, 0f, Color.TRANSPARENT) {
            background = it.tintSelected(normalColor, stateColor)
        }
    }
}

fun View.roundHeightEnabled(normalColor: Int, stateColor: Int) {
    forSureGetSize {
        roundInternal(height * 0.5f, normalColor, 0f, Color.TRANSPARENT) {
            background = it.tintEnable(normalColor, stateColor)
        }
    }
}

fun View.roundHeightEnabled(normalColors: IntArray, stateColors: IntArray) {
    forSureGetSize {
        enabled(roundDrawable(height * 0.5f, normalColors, GradientDrawable.Orientation.LEFT_RIGHT, 0f, Color.TRANSPARENT),
                roundDrawable(height * 0.5f, stateColors, GradientDrawable.Orientation.LEFT_RIGHT, 0f, Color.TRANSPARENT))
    }
}

fun View.roundHeightEnabled(normalColors: Array<String>, stateColors: Array<String>) {
    forSureGetSize {
        enabled(roundDrawable(height * 0.5f, normalColors.map { parseColor(it) }.toIntArray(), GradientDrawable.Orientation.LEFT_RIGHT, 0f, Color.TRANSPARENT),
                roundDrawable(height * 0.5f, stateColors.map { parseColor(it) }.toIntArray(), GradientDrawable.Orientation.LEFT_RIGHT, 0f, Color.TRANSPARENT))
    }
}

fun View.roundHeightLR(vararg solidColors: String,
                       strokeW: Float = 0f,
                       strokeColor: Int = Color.TRANSPARENT) {
    roundHeightLR(solidColors.map { parseColor(it) }.toIntArray(), strokeW, strokeColor)
}

fun View.roundHeightTB(vararg solidColors: String,
                       strokeW: Float = 0f,
                       strokeColor: Int = Color.TRANSPARENT) {
    forSureGetSize {
        roundInternal(height * 0.5f, solidColors.map { parseColor(it) }.toIntArray(), GradientDrawable.Orientation.TOP_BOTTOM, strokeW, strokeColor)
    }
}

fun View.roundHeightLR(solidColors: IntArray,
                       strokeW: Float = 0f,
                       strokeColor: Int = Color.TRANSPARENT) {
    forSureGetSize {
        roundInternal(height * 0.5f, solidColors, GradientDrawable.Orientation.LEFT_RIGHT, strokeW, strokeColor)
    }
}

/**
 * 四个角，r的size必须为4。分别为左上，右上、右下、左下
 * */
fun View.roundArray(r: FloatArray, solidColor: Int = Color.WHITE, strokeW: Float = 0f,
                    strokeColor: Int = Color.TRANSPARENT, dashW: Float = 0f,
                    dashGap: Float = 0f) {
    roundInternalArray(r.map { it.dp.toFloat() }.toFloatArray(), solidColor, strokeW, strokeColor, dashW, dashGap)
}

fun View.roundArrayLR(r: FloatArray, solidColors: IntArray, strokeW: Float = 0f,
                      strokeColor: Int = Color.TRANSPARENT) {
    roundInternalArray(r, solidColors, GradientDrawable.Orientation.LEFT_RIGHT, strokeW, strokeColor)
}

fun View.round(r: Float = 2f, solidColor: Int = Color.WHITE, strokeW: Float = 0f,
               strokeColor: Int = Color.TRANSPARENT, dashW: Float = 0f,
               dashGap: Float = 0f) {
    roundInternal(r.dp.toFloat(), solidColor, strokeW, strokeColor, dashW, dashGap)
}

fun View.roundLR(r: Float = 2f, solidColors: IntArray, strokeW: Float = 0f,
                 strokeColor: Int = Color.TRANSPARENT) {
    roundInternal(r, solidColors, GradientDrawable.Orientation.LEFT_RIGHT, strokeW, strokeColor)
}

fun View.roundInternal(r: Float = 2f.dp.toFloat(), solidColor: Int = Color.WHITE,
                       strokeW: Float = 0f, strokeColor: Int = Color.TRANSPARENT,
                       dashW: Float = 0f,
                       dashGap: Float = 0f,
                       drawableSetCallback: (Drawable) -> Unit = {}) {
    setRoundBackground(roundDrawable(r, solidColor, strokeW, strokeColor, dashW, dashGap), drawableSetCallback)
}

fun View.roundInternal(r: Float = 2f.dp.toFloat(), solidColor: IntArray,
                       orientation: GradientDrawable.Orientation,
                       strokeW: Float = 0f, strokeColor: Int = Color.TRANSPARENT,
                       dashW: Float = 0f,
                       dashGap: Float = 0f) {
    setRoundBackground(roundDrawable(r, solidColor, orientation, strokeW, strokeColor, dashW, dashGap))
}

fun View.roundInternalArray(r: FloatArray, solidColor: Int = Color.WHITE, strokeW: Float = 0f,
                            strokeColor: Int = Color.TRANSPARENT, dashW: Float = 0f,
                            dashGap: Float = 0f) {
    setRoundBackground(roundDrawable(r, solidColor, strokeW, strokeColor, dashW, dashGap))
}

fun View.roundInternalArray(r: FloatArray, solidColors: IntArray,
                            orientation: GradientDrawable.Orientation,
                            strokeW: Float = 0f,
                            strokeColor: Int = Color.TRANSPARENT,
                            dashW: Float = 0f,
                            dashGap: Float = 0f) {
    setRoundBackground(roundDrawable(r, solidColors, strokeW, strokeColor, dashW, dashGap, orientation))
}

private fun View.setRoundBackground(d: Drawable, drawableSetCallback: (Drawable) -> Unit = {}) {
    this.background = d
    drawableSetCallback.invoke(d)
    (d as? RoundGradientDrawable)?.isNeedClip = true
    //避免子View影响到背景, 硬件加速打开这个属性，但view没有附着到window的话，那么isHardwareAccelerated返回false
    if (!isAttachedToWindow || isHardwareAccelerated) {
        this.clipToOutline = true
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
fun CompoundButton.checkedButton(normal: Drawable, press: Drawable,
                                 drawableHandle: (StateListDrawable) -> Unit = {}) {
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
    val drawable = getCheckedDrawable(ColorDrawable(normalColor), ColorDrawable(checkedColor))
    drawableHandle.invoke(drawable)
    this.buttonDrawable = drawable
}

fun View.enabled(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    this.background = getEnableDrawable(normalRes, pressRes)
}

fun View.enabled(normal: Drawable, press: Drawable,
                 drawableHandle: (StateListDrawable) -> Unit = {}) {
    val drawable = getEnableDrawable(normal, press)
    drawableHandle.invoke(drawable)
    this.background = drawable
}

fun View.enabledColor(normalColor: Int, checkedColor: Int) {
    this.background = getEnableDrawable(ColorDrawable(normalColor), ColorDrawable(checkedColor))
}

fun View.checked(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int) {
    this.background = getCheckedDrawable(normalRes, pressRes)
}

fun View.checked(normal: Drawable, press: Drawable,
                 drawableHandle: (StateListDrawable) -> Unit = {}) {
    val checkedDrawable = getCheckedDrawable(normal, press)
    drawableHandle.invoke(checkedDrawable)
    this.background = checkedDrawable
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

@SuppressLint("ClickableViewAccessibility")
fun View.event(click: ((View) -> Unit)? = null, doubleTap: ((MotionEvent?) -> Unit)? = null,
               longPress: ((MotionEvent?) -> Unit)? = null,
               onTouch: ((MotionEvent) -> Unit)? = null) {
    this.isLongClickable = true
    val gestureDetector = GestureDetector(this.context, object :
            GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            Log.d("event", "onSingleTapConfirmed ")
            click?.apply {
                invoke(this@event)
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            Log.d("event", "onDoubleTap ")
            doubleTap?.apply {
                invoke(e)
            }
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            Log.d("event", "onLongPress ")
            longPress?.apply { invoke(e) }
        }
    })

    this.setOnTouchListener { v, event ->
        onTouch?.invoke(event)
        gestureDetector.onTouchEvent(event)
    }
}

fun ViewGroup.clipChild(isClip: Boolean) {
    clipToPadding = isClip
    clipChildren = isClip
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

fun View.interceptBackPress(onBackPress: (View) -> Unit) {
    isFocusableInTouchMode = true
    requestFocus()
    setOnKeyListener { v, keyCode, event ->
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
            onBackPress.invoke(this)
            clearAllInterceptBackPress()
            return@setOnKeyListener true
        }
        return@setOnKeyListener false
    }
}

fun View.clearAllInterceptBackPress() {
    setOnKeyListener(null)
    isFocusableInTouchMode = false
    clearFocus()
}

fun View.getLocationOnScreen(): IntArray {
    val array = IntArray(2)
    getLocationOnScreen(array)
    return array
}

fun View.getRectLocationOnScreen(): Rect {
    val array = getLocationOnScreen()
    return Rect(array[0], array[1], array[0] + width, array[1] + height)
}

fun View.getLocationOnWindow(): IntArray {
    val array = IntArray(2)
    getLocationInWindow(array)
    return array
}

fun View.getCurRectLocation(): Rect {
    val rect = Rect()
    getGlobalVisibleRect(rect)
    return rect
}

fun ViewGroup.childView(): ArrayList<View> {
    val list = arrayListOf<View>()
    val count = childCount
    for (i in 0 until count) {
        list.add(getChildAt(i))
    }
    return list
}

inline fun <reified C : View> ViewGroup.filterViewIsInstance(): List<C> {
    val list = arrayListOf<C>()
    list.addAll(filterViewIsInstanceOnce())
    val filterViewGroup = filterViewGroup()
    filterViewGroup.forEach {
        @Suppress("UNCHECKED_CAST")
        if (it is C) {
            list.add(it)
        } else {
            list.addAll(it.filterViewIsInstanceOnce())
        }
    }
    return list
}

fun ViewGroup.filterViewGroup(): List<ViewGroup> {
    val list = arrayListOf<ViewGroup>()
    childView().forEach {
        if (it is ViewGroup) {
            list.add(it)
            list.addAll(it.filterViewGroup())
        }
    }
    return list
}

inline fun <reified C : View> ViewGroup.filterViewIsInstanceOnce(): List<C> {
    val list = arrayListOf<C>()
    childView().forEach {
        @Suppress("UNCHECKED_CAST")
        if (it is C) {
            list.add(it)
        }
    }
    return list
}

fun ViewPager.setCurItemSafe(pos: Int) {
    if (pos < 0 || pos >= adapter?.count ?: 0) {
        return
    }
    if (pos == currentItem) {
        return
    }
    currentItem = pos
}

fun View.copyBackgroundColor(other: View): Boolean {
    val b = other.background
    b ?: return false
    if (b is ColorDrawable) {
        setBackgroundColor(b.color)
        return true
    }
    return false
}

fun ProgressBar.setMaxHeightSafe(v: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        maxHeight = 4f.dp
    } else {
        try {
            val declaredField = this::class.java.getDeclaredField("mMaxHeight")
            declaredField.isAccessible = true
            declaredField.setInt(this, 4f.dp)
        } catch (e: Exception) {
        }
    }
}

fun Array<out View>.setVisible(b: Boolean) {
    forEach { it.setVisible(b) }
}

@SuppressLint("ClickableViewAccessibility")
fun View.setOnGestureListener(listener: GestureDetector.OnGestureListener?) {
    listener?.let {
        val gestureDetector = GestureDetector(context, it)
        setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    } ?: run {
        setOnTouchListener(null)
    }
}