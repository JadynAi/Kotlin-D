package com.jadynai.kotlindiary.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.RotateDrawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.view.View
import com.jadyn.ai.kotlind.function.ui.getResDrawable
import com.jadyn.ai.kotlind.function.ui.rotate
import com.jadynai.kotlindiary.R
import com.jadynai.kotlindiary.view.path.PathTransition
import kotlin.properties.Delegates

/**
 *Jairett since 2021/12/23
 */
class PathTransitionRotateView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var curProgressV = 0f
    private val pathStart = "M0,0 Q20,35 68,48 Q110,62 110,106 Q109,143 131,151 L0,151 Z"
    private val pathEnd = "M0,100 Q2,58 50,46 Q104,30 110,0 Q110,0 110,151 L0,151 Z"
    private var curPath: Path? = null
    private val xFerMode by lazy { PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP) }
    private var bgBitmap: Bitmap? = null
    private var bgCanvas: Canvas? = null
    private val paint by lazy {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.style = Paint.Style.FILL
        p
    }

    private var pathMatrix: Matrix? = null

    private val pathTransition by lazy {
        PathTransition(pathStart, pathEnd) { curProgress, path ->
            curProgressV = curProgress
            curPath = path
            pathMatrix?.let { m ->
                path.transform(m)
            }
            invalidate()
        }
    }

    private var openState by Delegates.observable(false) { property, oldValue, newValue ->
        if (newValue) {
            pathTransition.start()
        } else {
            pathTransition.reverse()
        }
    }

    private val logo by lazy {
        val r = RotateDrawable()
        r.fromDegrees = 0f
        r.toDegrees = 720f
        r.drawable = getResDrawable(R.drawable.ic_logo)
        r
    }
    private val close by lazy {
        val r = RotateDrawable()
        r.fromDegrees = 0f
        r.toDegrees = 720f
        r.drawable = getResDrawable(R.drawable.ic_logo_close)
        r
    }
    private val bgStart by lazy {
        getResDrawable(R.drawable.bg_start)
    }
    private val bgEnd by lazy {
        getResDrawable(R.drawable.bg_end_final)
    }

    init {
        handleClick()
    }

    fun setProgress(progress: Float) {
        pathTransition.progress = progress
    }

    private fun handleClick() {
        setOnClickListener {
            openState = !openState
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        bgStart?.setBounds(0, 0, width, height)
        bgEnd?.setBounds(0, 0, width, height)
        logo.let { l ->
            l.setBounds(10, height - l.intrinsicHeight - 10, 10 + l.intrinsicWidth, height - 10)
        }
        close.let { c ->
            c.setBounds(10, height - c.intrinsicHeight - 10,
                10 + c.intrinsicWidth, height - 10)
        }
        if (pathMatrix == null) {
            val m = Matrix()
            val ratio = width / 131f
            m.setScale(ratio, ratio, 0f, 0f)
            pathMatrix = m
        }
        if (bgBitmap == null) {
            val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            bgBitmap = b
            bgCanvas = c
        }
        pathTransition.progress = 0f
    }

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        curPath?.let { p ->
            canvas.drawPath(p, paint)
            paint.xfermode = xFerMode
            val bgC = bgCanvas
            val bgB = bgBitmap
            if (bgC != null && bgB != null) {
                canvas.save()
                bgC.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
                if (curProgressV in 0f..0.5f) {
                    bgStart?.draw(bgC)
                    logo.level = (10000 * curProgressV).toInt()
                    logo.draw(bgC)
                } else {
                    bgEnd?.draw(bgC)
                    close.level = (10000 * curProgressV).toInt()
                    close.draw(bgC)
                }
                canvas.drawBitmap(bgB, 0f, 0f, paint)
                canvas.restore()
            }
            paint.xfermode = null
        }
        canvas.restoreToCount(count)
    }
}