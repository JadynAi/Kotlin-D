package com.jadyn.ai.kotlind.function.ui

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.util.Size
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.jadyn.ai.kotlind.base.KD
import com.jadyn.ai.kotlind.utils.dp
import com.jadyn.ai.kotlind.utils.dp2px
import com.jadyn.ai.kotlind.utils.parseColor

/**
 *@version:
 *@FileDescription: Drawable相关代码
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
//------enable------
fun getEnableDrawable(normal: String, changed: String): StateListDrawable {
    return getEnableDrawable(ColorDrawable(parseColor(normal)), ColorDrawable(parseColor(changed)))
}

fun getEnableDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getEnableDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getEnableDrawable(nor: Drawable?, press: Drawable?): StateListDrawable {
    return getStateDrawable(nor, press, android.R.attr.state_enabled)
}

//------press------
fun getPressDrawable(nor: String, pressColor: String): StateListDrawable {
    return getPressDrawable(ColorDrawable(parseColor(nor)), ColorDrawable(parseColor(pressColor)))
}

fun getPressDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getPressDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getPressDrawable(nor: Drawable?, press: Drawable?): StateListDrawable {
    return getStateDrawable(nor, press, android.R.attr.state_pressed)
}

//------check------
fun getCheckedDrawable(normalRes: String, checkedColor: String): StateListDrawable {
    return getCheckedDrawable(ColorDrawable(parseColor(normalRes)), ColorDrawable(parseColor(checkedColor)))
}

fun getCheckedDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getCheckedDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getCheckedDrawable(nor: Drawable?, checked: Drawable?): StateListDrawable {
    return getStateDrawable(nor, checked, android.R.attr.state_checked)
}

//------select------
fun getSelectedDrawable(nor: String, checkedColor: String): StateListDrawable {
    return getSelectedDrawable(ColorDrawable(parseColor(nor)), ColorDrawable(parseColor(checkedColor)))
}

fun getSelectedDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getSelectedDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getSelectedDrawable(nor: Drawable?, checked: Drawable?): StateListDrawable {
    return getStateDrawable(nor, checked, android.R.attr.state_selected)
}

//------select------
fun getActivatedDrawable(nor: String, checkedColor: String): StateListDrawable {
    return getActivatedDrawable(ColorDrawable(parseColor(nor)), ColorDrawable(parseColor(checkedColor)))
}

fun getActivatedDrawable(@DrawableRes normalRes: Int, @DrawableRes pressRes: Int): StateListDrawable {
    return getActivatedDrawable(getResDrawable(normalRes), getResDrawable(pressRes))
}

fun getActivatedDrawable(nor: Drawable?, checked: Drawable?): StateListDrawable {
    return getStateDrawable(nor, checked, android.R.attr.state_activated)
}

fun getStateDrawable(nor: Drawable?, checked: Drawable?, @AttrRes state: Int): StateListDrawable {
    val sd = StateListDrawable()
    checked?.apply {
        sd.addState(intArrayOf(state), this)
    }
    nor?.apply {
        sd.addState(intArrayOf(), this)
    }
    return sd
}

fun getStateDrawable(nor: Drawable?, vararg stateDrawables: Pair<Drawable?, Int>): StateListDrawable {
    val sd = StateListDrawable()
    stateDrawables.forEach {
        it.first?.let { drawable ->
            sd.addState(intArrayOf(it.second), drawable)
        }
    }
    nor?.apply {
        sd.addState(intArrayOf(), this)
    }
    return sd
}

fun getLevelDrawable(@DrawableRes vararg ids: Int): Drawable {
    val list = ids.map {
        getResDrawable(it)!!
    }.toTypedArray()
    return getLevelDrawable(*list)
}

fun getLevelDrawable(vararg ds: Drawable): Drawable {
    val drawable = LevelListDrawable()
    ds.forEachIndexed { index, d ->
        val i = index + 1
        drawable.addLevel(i, i, d)
    }
    return drawable
}

fun roundDrawable(r: Float = dp2px(2f).toFloat(), solidColor: Int = Color.WHITE,
                  strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT,
                  dashW: Float = 0f,
                  dashGap: Float = 0f): GradientDrawable {
    return roundDrawable(floatArrayOf(r, r, r, r), intArrayOf(solidColor), strokeW, strokeColor, dashW, dashGap)
}

fun roundDrawable(r: Float = dp2px(2f).toFloat(),
                  solidColors: IntArray = intArrayOf(Color.WHITE),
                  orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
                  strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT,
                  dashW: Float = 0f,
                  dashGap: Float = 0f
): GradientDrawable {
    return roundDrawable(floatArrayOf(r, r, r, r), solidColors, strokeW, strokeColor, dashW, dashGap, orientation)
}

fun roundDrawable(r: Float = dp2px(2f).toFloat(),
                  solidColors: Array<String> = arrayOf("#ffffff"),
                  orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM,
                  strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT,
                  dashW: Float = 0f,
                  dashGap: Float = 0f
): GradientDrawable {
    return roundDrawable(floatArrayOf(r, r, r, r), solidColors.map { parseColor(it) }.toIntArray(), strokeW, strokeColor, dashW, dashGap, orientation)
}

fun roundDrawable(rArray: FloatArray, solidColor: Int = Color.WHITE, strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT, dashW: Float = 0f,
                  dashGap: Float = 0f): GradientDrawable {
    return roundDrawable(rArray, intArrayOf(solidColor), strokeW, strokeColor, dashW, dashGap)
}

fun roundDrawable(rArray: FloatArray, solidColors: IntArray = intArrayOf(Color.WHITE), strokeW: Float = 0f,
                  strokeColor: Int = Color.TRANSPARENT,
                  dashW: Float = 0f,
                  dashGap: Float = 0f,
                  orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.TOP_BOTTOM): GradientDrawable {
    require(rArray.size == 4) { "round corner size must is 4!!!" }
    val gradientDrawable = RoundGradientDrawable()
    gradientDrawable.shape = GradientDrawable.RECTANGLE
    if (solidColors.size > 1) {
        gradientDrawable.orientation = orientation
        gradientDrawable.colors = solidColors
    } else {
        gradientDrawable.setColor(solidColors.first())
    }
    if (dashW <= 0f) {
        gradientDrawable.setStroke(dp2px(strokeW), strokeColor)
    } else {
        val gap = if (dashGap > 0) dashGap else dashW
        gradientDrawable.setStroke(dp2px(strokeW), strokeColor, dashW.dp.toFloat(), gap.dp.toFloat())
    }
    if (rArray[0] == rArray[1] && rArray[2] == rArray[3] && rArray[1] == rArray[2]) {
        gradientDrawable.cornerRadius = rArray[0]
    } else {
        gradientDrawable.cornerRadii = floatArrayOf(
                rArray[0], rArray[0],
                rArray[1], rArray[1],
                rArray[2], rArray[2],
                rArray[3], rArray[3]
        )
    }
    return gradientDrawable
}

class RoundGradientDrawable : GradientDrawable() {

    private var roundPath: Path? = null
    private var size: Size? = null
    private var radiiC: FloatArray? = null
    var isNeedClip = false

    override fun setCornerRadii(radii: FloatArray?) {
        radiiC = radii?.clone()
        super.setCornerRadii(radii)
    }

    override fun setCornerRadius(radius: Float) {
        if (radius != 0f) {
            radiiC = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        }
        super.setCornerRadius(radius)
    }

    override fun draw(canvas: Canvas) {
        if (isNeedClipRound(canvas)) {
            tryInitClipPath()
            roundPath?.let { canvas.clipPath(it) }
        }
        super.draw(canvas)
    }

    /**
     * canvas判断是否被添加到关闭了硬件加速的视图中。
     * 如果硬件加速关闭，round需要clip
     * 作为背景时才需要clip
     * */
    private fun isNeedClipRound(canvas: Canvas): Boolean {
        return !canvas.isHardwareAccelerated && isNeedClip
    }

    private fun tryInitClipPath() {
        val bounds = bounds
        val width = bounds.width()
        val height = bounds.height()
        if (width != 0 && height != 0 && size == null) {
            size = Size(width, height)
            val radiiCL = radiiC
            if (roundPath == null && radiiCL != null) {
                val path = Path()
                path.addRoundRect(0f, 0f, width.toFloat(), height.toFloat(), radiiCL, Path.Direction.CW)
                roundPath = path
            }
        }
    }
}

fun ovalDrawable(solidColor: Int, strokeW: Float = 0f,
                 strokeColor: Int = Color.TRANSPARENT, w: Int = 0, h: Int = 0): GradientDrawable {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.OVAL
    if (w != 0 && h != 0) {
        drawable.setSize(w, h)
    }
    drawable.setColor(solidColor)
    drawable.setStroke(dp2px(strokeW), strokeColor)
    return drawable
}

fun getResDrawable(resId: Int, context: Context? = null): Drawable? {
    return try {
        ContextCompat.getDrawable(context ?: KD.applicationWrapper(), resId)?.mutate()
    } catch (e: Exception) {
        null
    }
}

fun Context.getResDrawable(resId: Int): Drawable? {
    return getResDrawable(resId, this)
}

fun Drawable.tintSelected(@ColorInt nor: Int, @ColorInt state: Int): Drawable {
    return tintState(nor, state, R.attr.state_selected)
}

fun Drawable?.color(color: Int): Drawable? {
    this?.mutate()?.let {
        colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        return it
    }
    return null
}

fun Drawable?.rotate(degree: Float): Drawable? {
    this?.mutate()?.let {
        val rotateDrawable = RotateDrawable()
        rotateDrawable.drawable = it
        rotateDrawable.pivotX = 0.5f
        rotateDrawable.pivotY = 0.5f
        // 设置level让drawable旋转
        rotateDrawable.fromDegrees = degree
        rotateDrawable.toDegrees = degree
        rotateDrawable.level = 10000
        return rotateDrawable
    }
    return null
}

fun Drawable.tintEnable(@ColorInt nor: Int, @ColorInt state: Int): Drawable {
    return tintState(nor, state, R.attr.state_enabled)
}

fun Drawable.tintChecked(@ColorInt nor: Int, @ColorInt state: Int): Drawable {
    return tintState(nor, state, android.R.attr.state_checked)
}

/**
 * ColorStateList 两个参数，第一参数就是状态数组。第二个参数为状态对应的颜色
 *
 * @param nor state false状态下的颜色
 * @param state State触发时，为true的颜色
 * @param stateId
 * */
fun Drawable.tintState(@ColorInt nor: Int, @ColorInt state: Int, @AttrRes stateId: Int): Drawable {
    // 将drawable 变成一个可改变的状态，不会影响其他从同一个资源加载进来的drawable实例
    mutate()
    val d = DrawableCompat.wrap(this)
    DrawableCompat.setTintList(d, ColorStateList(arrayOf(intArrayOf(stateId), intArrayOf()), intArrayOf(state, nor)))
    return d
}

fun Drawable.tintState(colors: IntArray, stateIds: IntArray): Drawable {
    // 将drawable 变成一个可改变的状态，不会影响其他从同一个资源加载进来的drawable实例
    mutate()
    val d = DrawableCompat.wrap(this)
    DrawableCompat.setTintList(d, ColorStateList(arrayOf(intArrayOf(), *stateIds.map { intArrayOf(it) }.toTypedArray()), colors))
    return d
}


/**
 * 为drawable里的文件添加一个圆形背景，主要用在一些可以直接设置drawable附加物的组件上，譬如给textview设置drawableTop.
 * 避免使用多个组件引起的麻烦
 * */
class ResourceCircleDrawable(r: Float, color: Int, @DrawableRes private val resID: Int) : GradientDrawable() {

    constructor(r: Float, colorS: String, @DrawableRes resID: Int) : this(r, parseColor(colorS), resID)

    init {
        val dpValue = dp2px(r * 2)
        shape = OVAL
        setSize(dpValue, dpValue)
        setColor(color)
//        colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        BitmapFactory.decodeResource(KD.applicationWrapper().resources, resID)
                ?.let {
                    canvas.drawBitmap(it, (intrinsicWidth - it.width) * 0.5f,
                            (intrinsicHeight - it.height) * 0.5f, null)
                }
    }
}

class StrokeGradientLRDrawable(colors: IntArray,
                               radius: Float,
                               strokeWidth: Float) : ShapeDrawable() {

    init {
        // 外部矩形弧度
        val outerR = floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius)
        // 内部矩形与外部矩形的距离
        val inset = RectF(strokeWidth, strokeWidth, strokeWidth, strokeWidth)
        // 内部矩形弧度
        val innerRadius = radius - strokeWidth
        val innerRadii = floatArrayOf(innerRadius, innerRadius, innerRadius, innerRadius, innerRadius, innerRadius, innerRadius, innerRadius)
        val rr = RoundRectShape(outerR, inset, innerRadii)
        shape = rr
        shaderFactory = object : ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(0f, 0f, width.toFloat(),
                    0f, colors, null, Shader.TileMode.CLAMP)
            }
        }
    }
}
