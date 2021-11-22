package com.jadynai.kotlindiary.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.jadyn.ai.kotlind.utils.resources
import com.jadynai.kotlindiary.R

/**
 *Jairett since 2021/11/18
 */
class ArcView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var clipPath: Path? = null
    private var srcBitmap: Bitmap? = null
    var topRadius = 50f
    private val xFerMode by lazy { PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP) }
    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }
    }
    private var initWidth = 0
    private var initHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        if (initWidth == 0 && initHeight == 0) {
            initWidth = measuredWidth
            initHeight = measuredHeight
        }
        clipPath = getClipPath(measuredWidth.toFloat(), measuredHeight.toFloat(),
            measuredHeight * 0.5f)
    }

    override fun onDraw(canvas: Canvas) {
        val count = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        if (srcBitmap == null) {
            srcBitmap = getSrcBitmap()
        }
        clipPath?.let {
            canvas.drawPath(it, paint)
            paint.xfermode = xFerMode
            srcBitmap?.let { src ->
                canvas.save()
                if (src.width < width) {
                    canvas.scale(width.toFloat() / initWidth,
                        height.toFloat() / initHeight, 0.5f, 0.5f)
                } else if (width < src.width) {
                    canvas.scale(width.toFloat() / src.width,
                        height.toFloat() / src.height, 0.5f, 0.5f)
                }
                canvas.drawBitmap(src, 0f, 0f, paint)
                canvas.restore()
            }
            paint.xfermode = null
        }
        canvas.restoreToCount(count)
    }

    private fun getSrcBitmap(): Bitmap? {
        val d = drawable ?: return null
        val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        imageMatrix?.let { matrix ->
            c.concat(matrix)
        }
        d.draw(c)
        return b
    }

    private fun getClipPath(width: Float, height: Float, orignalHeight: Float): Path {
        val path = Path()
        path.moveTo(0f, topRadius)
        path.lineTo(0f, orignalHeight)
        path.cubicTo(
            0f, orignalHeight, (width / 2),
            (height - 0.5f * orignalHeight) * 2f,
            width, orignalHeight
        )
        path.lineTo(width, topRadius)
        path.quadTo(width, 0f, width - topRadius, 0f)
        path.lineTo(topRadius, 0f)
        path.quadTo(0f, 0f, 0f, topRadius)
        path.close()
        return path
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        srcBitmap = null
    }
}