package com.jadynai.kotlindiary.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
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
) : ViewGroup(context, attrs, defStyle) {

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "onMeasure : $widthMeasureSpec : $heightMeasureSpec")
        //求二进制低30位
        val width = MeasureSpec.getSize(widthMeasureSpec)
        //求二进制高两位
        val modeW = MeasureSpec.getMode(widthMeasureSpec)

        val height = MeasureSpec.getSize(heightMeasureSpec)
        val modeH = MeasureSpec.getMode(heightMeasureSpec)


        val childCount = childCount
        Log.d(TAG, "measure child count: ")
        var frameW = 0
        var frameH = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val params = child.layoutParams as MarginLayoutParams
            val childW = child.measuredWidth + params.leftMargin + params.rightMargin
            val childH = child.measuredHeight + params.topMargin + params.bottomMargin
        }

        setMeasuredDimension(
                if (modeW == MeasureSpec.EXACTLY) width else 300,
                if (modeH == MeasureSpec.EXACTLY) height else 300
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d(TAG, "onLayout: ")
        var curHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val params = child.layoutParams as MarginLayoutParams
            val childW = child.measuredWidth + params.leftMargin + params.rightMargin
            val childH = child.measuredHeight + params.topMargin + params.bottomMargin

            child.layout(paddingLeft, curHeight, childW, curHeight + childH)
            curHeight += childH
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)
        Log.d(TAG, "dispatchTouchEvent :$dispatchTouchEvent and ${ev?.action}")
        ev?.pointerCount
        return dispatchTouchEvent
    }

    private var downx = 0f

//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        ev?.apply {
//            var intercept = false
//            intercept = when (action) {
//                MotionEvent.ACTION_DOWN -> {
//                    false
//                }
//                else -> {
//                    true
//                }
//            }
//            return intercept
//        }
//        val onInterceptTouchEvent = super.onInterceptTouchEvent(ev)
//        Log.d(TAG, "onInterceptTouchEvent:$onInterceptTouchEvent ${ev?.action}")
//        return true
//    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "onTouchEvent: ${event?.action}")
        return super.onTouchEvent(event)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,
                MarginLayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }
}

class ViewP @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d("cece", " id : $id ondraw")
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

class ViewText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val tv by lazy {
        val textView = TextView(context)
        textView.setTextColor(Color.RED)
        textView.textSize = 12f
        textView.gravity = Gravity.CENTER
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        textView.layoutParams = layoutParams
        textView
    }

    private val bgPaint by lazy {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.RED
        p.alpha = 122
        p.style = Paint.Style.FILL
        p
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        tv.measure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        tv.layout(l, t, r, b)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        tv.text = "adadad"
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)
        tv.draw(canvas)
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
    }

}
        