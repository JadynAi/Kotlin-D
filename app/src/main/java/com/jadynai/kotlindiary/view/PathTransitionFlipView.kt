package com.jadynai.kotlindiary.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.jadyn.ai.kotlind.function.ui.getResDrawable
import com.jadynai.kotlindiary.R
import com.jadynai.kotlindiary.view.path.PathTransition
import kotlin.properties.Delegates

/**
 *Jairett since 2021/12/23
 */
class PathTransitionFlipView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var curProgressV = 0f
    private val pathStart = "M0,0 Q20,35 68,48 Q110,62 110,106 Q109,143 131,151 L0,151 Z"
    private val pathEnd = "M0,100 Q2,50 50,50 Q98,50 110,0 Q110,0 110,151 L0,151 Z"
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

    private val camera by lazy {
        val c = Camera()
        c.setLocation(0f, 0f, -20f)
        c
    }

    private val drawMatrix by lazy { Matrix() }
    private var logo: Bitmap? = null
    private var close: Bitmap? = null
    private val bgStart by lazy {
        getResDrawable(R.drawable.bg_start)
    }
    private val bgEnd by lazy {
        getResDrawable(R.drawable.bg_end_final)
    }
    private var drawRect: RectF? = null

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
        val offset = (width * (8f / 131f))
        val w = (width * (92f / 131f)).toInt()
        if (logo == null) {
            logo = getResDrawable(R.drawable.ic_logo)?.toBitmap(w, w)
        }
        if (close == null) {
            close = getResDrawable(R.drawable.ic_logo_close_pic)?.toBitmap(w, w)
        }
        if (drawRect == null) {
            drawRect = RectF(offset, height - offset - w, offset + w, height - offset)
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
                if (curProgressV in 0f..0.5f) {
                    bgStart?.draw(bgC)
                    drawLogo(bgC)
                } else {
                    bgEnd?.draw(bgC)
                    drawLogoClose(bgC)
                }
                canvas.drawBitmap(bgB, 0f, 0f, paint)
                canvas.restore()
            }
            paint.xfermode = null
        }
        canvas.restoreToCount(count)
    }

    private fun drawLogo(canvas: Canvas) {
        if (curProgressV > 0.5f) return
        val l = logo ?: return
        val rect = drawRect ?: return
        canvas.save()
        canvas.clipRect(rect.left, rect.top, rect.right, rect.bottom)
        camera.save()
        camera.rotateY(-90f * (curProgressV / 0.5f))
        camera.getMatrix(drawMatrix)
        camera.restore()
        drawMatrix.preScale((0.5f - curProgressV) / 0.5f, 1f)
        drawMatrix.preTranslate(-rect.centerX(), -rect.centerY())
        drawMatrix.postTranslate(rect.centerX(), rect.centerY())
        drawMatrix.postTranslate(rect.left, rect.top)
        canvas.drawBitmap(l, drawMatrix, null)
        canvas.restore()
    }

    private fun drawLogoClose(canvas: Canvas) {
        if (curProgressV < 0.5f) return
        val c = close ?: return
        val rect = drawRect ?: return
        canvas.save()
        canvas.clipRect(rect.left, rect.top, rect.right, rect.bottom)
        camera.save()
        camera.rotateY(90f * ((1f - curProgressV) / 0.5f))
        camera.getMatrix(drawMatrix)
        camera.restore()
        drawMatrix.preScale((curProgressV - 0.5f) / 0.5f, 1f)
        drawMatrix.preTranslate(-rect.centerX(), -rect.centerY())
        drawMatrix.postTranslate(rect.centerX(), rect.centerY())
        drawMatrix.postTranslate(rect.left, rect.top)
        canvas.drawBitmap(c, drawMatrix, null)
        canvas.restore()
    }
}