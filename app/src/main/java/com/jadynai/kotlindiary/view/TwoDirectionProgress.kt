package com.jadynai.kotlindiary.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jadyn.ai.kotlind.function.ui.ovalDrawable
import com.jadyn.ai.kotlind.function.ui.roundDrawable
import com.jadyn.ai.kotlind.function.ui.roundHeight
import com.jadyn.ai.kotlind.utils.dp
import com.jadyn.ai.kotlind.utils.parseColor

/**
 *JadynAi since 2021/3/31
 */
class TwoDirectionProgress @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var max: Int = 100
    var progress: Int = 0

    // 起始位置
    var startProgress: Int = 50
    private var startPos = 0
    private val littleR by lazy { 5f.dp.toFloat() }
    private val circleThumbPaint by lazy {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.WHITE
        p.style = Paint.Style.FILL
        p
    }
    private val thumbBigDrawable by lazy { ovalDrawable(parseColor("#FFEE00")) }
    private var curX = 0
    private var progressDrawable: Drawable? = null
    private var leftProgressDrawable: Drawable? = null
    private var halfH = 0

    init {
        roundHeight(parseColor("#1a1a1a"))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (progressDrawable == null) {
            startPos = ((startProgress.toFloat() / max) * width).toInt()
            curX = startPos
            halfH = (height * 0.5f).toInt()
            thumbBigDrawable.setBounds(startPos - halfH, 0, startPos + halfH, height)
            progressDrawable = roundDrawable(floatArrayOf(0f, height * 0.5f, height * 0.5f, 0f),
                    intArrayOf(parseColor("#FFEE00"), parseColor("#FFD200")),
                    orientation = GradientDrawable.Orientation.LEFT_RIGHT)
            leftProgressDrawable = roundDrawable(floatArrayOf(height * 0.5f, 0f, 0f, height * 0.5f),
                    intArrayOf(parseColor("#FFEE00"), parseColor("#FFD200")),
                    orientation = GradientDrawable.Orientation.RIGHT_LEFT)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                curX = event.x.toInt().coerceAtMost(width - halfH).coerceAtLeast(halfH)
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        thumbBigDrawable.draw(canvas)
        if (curX > startPos) {
            progressDrawable?.let {
                it.setBounds(startPos, 0, curX + halfH, height)
                it.draw(canvas)
            }
        } else {
            leftProgressDrawable?.let {
                it.setBounds(curX - halfH, 0, startPos, height)
                it.draw(canvas)
            }
        }
        canvas.drawCircle(curX.toFloat(), height * 0.5f, littleR, circleThumbPaint)
    }
}