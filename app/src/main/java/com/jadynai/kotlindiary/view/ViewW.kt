package com.jadynai.kotlindiary.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.*
import android.os.Build
import android.os.Looper
import android.text.DynamicLayout
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import com.jadyn.ai.kotlind.base.KD
import com.jadyn.ai.kotlind.utils.dp
import com.jadyn.ai.kotlind.utils.dp2px
import com.jadyn.ai.kotlind.utils.parseColor
import kotlin.math.abs

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-08-13
 *@ChangeList:
 */
class ViewW @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val TAG = "ViewTest"

    private val velocityTracker by lazy {
        VelocityTracker.obtain()
    }

    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            scrollTo(distanceX.toInt(), distanceY.toInt())
            return true
        }
    })

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        Log.d(TAG, "onMeasure : $widthMeasureSpec : $heightMeasureSpec")
//        //求二进制低30位
//        val width = MeasureSpec.getSize(widthMeasureSpec)
//        //求二进制高两位
//        val modeW = MeasureSpec.getMode(widthMeasureSpec)
//
//        val height = MeasureSpec.getSize(heightMeasureSpec)
//        val modeH = MeasureSpec.getMode(heightMeasureSpec)
//
//
//        val childCount = childCount
//        Log.d(TAG, "measure child count: ")
//        var frameW = 0
//        var frameH = 0
//        for (i in 0 until childCount) {
//            val child = getChildAt(i)
//            measureChild(child, widthMeasureSpec, heightMeasureSpec)
//            val params = child.layoutParams as MarginLayoutParams
//            val childW = child.measuredWidth + params.leftMargin + params.rightMargin
//            val childH = child.measuredHeight + params.topMargin + params.bottomMargin
//        }
//
//        setMeasuredDimension(
//                if (modeW == MeasureSpec.EXACTLY) width else 300,
//                if (modeH == MeasureSpec.EXACTLY) height else 300
//        )
//    }
//
//    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
//        Log.d(TAG, "onLayout: ")
//        var curHeight = 0
//        for (i in 0 until childCount) {
//            val child = getChildAt(i)
//            val params = child.layoutParams as MarginLayoutParams
//            val childW = child.measuredWidth + params.leftMargin + params.rightMargin
//            val childH = child.measuredHeight + params.topMargin + params.bottomMargin
//
//            child.layout(paddingLeft, curHeight, childW, curHeight + childH)
//            curHeight += childH
//        }
//    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("cecececece", "onDraw: test handler async draw end")
    }

//    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
//        return MarginLayoutParams(context, attrs)
//    }
//
//    override fun generateDefaultLayoutParams(): LayoutParams {
//        return MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,
//                MarginLayoutParams.WRAP_CONTENT)
//    }
//
//    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
//        return MarginLayoutParams(p)
//    }
}

class ViewP @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("cecela", " id : $id ondraw")
    }
}

class ViewChild @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        style: Int = 0
) : View(context, attrs, style) {

    private var downX = 0f
    private var downY = 0f


    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    this@ViewChild.parent.requestDisallowInterceptTouchEvent(true)
                    downX = x
                    downY = y
                }
                MotionEvent.ACTION_MOVE -> {
                    if (abs(x - downX) > abs(y - downY)) {
                        this@ViewChild.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                MotionEvent.ACTION_UP -> {

                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = x
                    downY = y
                }
                MotionEvent.ACTION_MOVE -> {
                    translationX += x - downX
                    translationY += y - downY
                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    downY = 0f
                    downX = 0f
                }
            }
            return true
        }
        return super.onTouchEvent(event)
    }
}

class VideoFrameLoadingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var total = 1
    private var isPrepareLoading = true

    private val path by lazy { Path() }
    private val pathMeasure by lazy { PathMeasure() }
    private val segmentPath by lazy { Path() }
    private val bgRect by lazy {
        RectF(0f, 0f, 0f, 0f)
    }

    private val paint by lazy {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = parseColor("#ffee00")
        p.style = Paint.Style.STROKE
        p.strokeWidth = 10f.dp.toFloat()
        // 2021/5/17-18:10 画笔末端设置为圆角
        p.strokeCap = Paint.Cap.ROUND
        p
    }

    private val bgPaint by lazy {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = parseColor("#80000000")
        p.style = Paint.Style.FILL
        p
    }

    private val tp by lazy {
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.textSize = 28f.dp.toFloat()
        textPaint.color = Color.WHITE
        textPaint.isFakeBoldText = true
        textPaint
    }

    private val textH by lazy {
        val fontMetrics = tp.fontMetrics
        abs(fontMetrics.bottom - fontMetrics.top)
    }

    private val baseLineGap by lazy {
        val fontMetrics = tp.fontMetrics
        abs(fontMetrics.leading) + abs(fontMetrics.ascent)
    }

    private var textW = 0f

    private var progressText = ""

    private var curRound = 0f

    fun setRound(r: Float) {
        curRound = r.dp.toFloat()
        // 2021/5/17-18:09 path 转角设置为圆角
        bgPaint.pathEffect = CornerPathEffect(curRound)
        if (width <= 0) return
        setPath()
    }

    fun setProgressColor(color: Int) {
        paint.setColor(color)
    }

    fun setProgressColor(colorS: String) {
        setProgressColor(parseColor(colorS))
    }

    fun setProgressWidth(w: Float) {
        paint.strokeWidth = w.dp.toFloat()
    }

    fun setProgress(@FloatRange(from = 0.0, to = 100.0) fl: Float) {
        val curLength = pathMeasure.length * fl / 100
        segmentPath.reset()
        pathMeasure.getSegment(0f, curLength, segmentPath, true)
        progressText = "progress $fl"
        textW = tp.measureText(progressText)
        if (Looper.getMainLooper().thread != Thread.currentThread()) {
            postInvalidate()
        } else {
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setPath()
        bgRect.set(0f, 0f, width.toFloat(), height.toFloat())
    }

    private fun setPath() {
        path.reset()
        val gap = paint.strokeWidth * 0.5f
        path.moveTo(curRound, gap)
        path.lineTo(width.toFloat() - curRound, gap)
        path.quadTo(width.toFloat() - gap, gap, width.toFloat() - gap, curRound)
        path.lineTo(width.toFloat() - gap, height - curRound)
        path.quadTo(width.toFloat() - gap, height.toFloat() - gap, width - curRound,
                height.toFloat() - gap)
        path.lineTo(curRound, height.toFloat() - gap)
        path.quadTo(gap, height.toFloat() - gap, gap, height - curRound)
        path.lineTo(gap, curRound)
        path.quadTo(gap, gap, curRound, gap)
        pathMeasure.setPath(path, true)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (isPrepareLoading) {
            canvas.drawRect(bgRect, bgPaint)
            canvas.drawPath(segmentPath, paint)
            canvas.drawText(progressText, (width - textW) * 0.5f,
                    (height - textH) * 0.5f + baseLineGap, tp)
        }
    }

    fun stopProgress() {
        isPrepareLoading = false
        invalidate()
    }

}

class ViewText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val cb by lazy {
        object : Drawable.Callback {
            override fun unscheduleDrawable(who: Drawable, what: Runnable) {
            }

            override fun invalidateDrawable(who: Drawable) {
                invalidate()
            }

            override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
            }

        }
    }

    private var drawable: GradientDrawable? = null
    private var valueAnimator: ValueAnimator? = null
    private var radius = dp2px(12f).toFloat()

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (drawable == null) {
            val intArrayOf = intArrayOf(
                    parseColor("#f8c002"),
                    parseColor("#ffca02"),
                    parseColor("#FFD200"),
                    parseColor("#FFEE00")
            )
//            vald intArrayOf = intArrayOf(Color.WHITE, Color.BLACK)
            val d = GradientDrawable()
            d.colors = intArrayOf
            d.cornerRadius = radius
            background = d
            d.callback = cb
            d.gradientType = GradientDrawable.RADIAL_GRADIENT
            val wF = width.toFloat()
            val hF = height.toFloat()
            d.gradientRadius = wF * 0.65f
            drawable = d
            val path = Path()
            val yGap = hF * 0.2f
            val r = radius * 1.2f
            path.moveTo(r, -yGap)
            path.lineTo(wF - r, -yGap)
            path.quadTo(wF - r * 0.5f, r * 0.5f, wF, r)
            path.lineTo(wF, hF - r)
            path.quadTo(wF - r * 0.5f, hF - r * 0.5f, wF - r, hF)
            path.lineTo(r, hF + yGap)
            path.close()
            val pathMeasure = PathMeasure(path, true)
            val v = ValueAnimator()
            v.duration = 10000
            v.setFloatValues(0f, pathMeasure.length)
            v.interpolator = LinearInterpolator()
            v.repeatCount = ValueAnimator.INFINITE
            val xy = floatArrayOf(0f, 0f)
            v.addUpdateListener {
                val fl = it.animatedValue as Float
                pathMeasure.getPosTan(fl, xy, null)
                drawable?.setGradientCenter(xy[0] / wF, xy[1] / hF)
            }
            valueAnimator = v
            v.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d("cece", "onDetachedFromWindow: ")
        valueAnimator?.end()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        Log.d("cecece", "onVisibilityChanged: $visibility")
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Log.d("cecece", "onWindowVisibilityChanged: $visibility")
    }

    override fun dispatchVisibilityChanged(changedView: View, visibility: Int) {
        super.dispatchVisibilityChanged(changedView, visibility)
        Log.d("cecece", "dispatchVisibilityChanged: $visibility")
        if (visibility != VISIBLE) {
            valueAnimator?.pause()
        } else {
            if (valueAnimator?.isStarted == true) {
                valueAnimator?.resume()
            } else {
                valueAnimator?.start()
            }
        }
    }

    override fun dispatchWindowVisibilityChanged(visibility: Int) {
        super.dispatchWindowVisibilityChanged(visibility)
        Log.d("cecece", "dispatchWindowVisibilityChanged: $visibility")
    }

//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//        cXY?.let {
//            canvas?.drawCircle(it[0], it[1], 5f, p)
//        }
//    }
}

class ResourceCircleDrawable(r: Float, color: Int, @DrawableRes private val resID: Int) : GradientDrawable() {

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
        