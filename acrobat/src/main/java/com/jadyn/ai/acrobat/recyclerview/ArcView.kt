package com.jadyn.ai.acrobat.recyclerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

/**
 *Jairett since 2021/11/18
 */
class ArcView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private var clipPath: Path? = null
    var topRadius = 50f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = measuredWidth
        val h = measuredHeight
        clipPath = getClipPath(w.toFloat(), h.toFloat(), h * 0.5f)
    }

    override fun draw(canvas: Canvas) {
        clipPath?.let {
            canvas.clipPath(it)
        }
        super.draw(canvas)
    }

    private fun getClipPath(width: Float, height: Float, orignalHeight: Float): Path {
        val path = Path()
        path.moveTo(0f, topRadius)
        path.lineTo(0f, orignalHeight)
        path.cubicTo(0f, orignalHeight, (width / 2),
            (height - 0.5f * orignalHeight) * 2f,
            width, orignalHeight)
        path.lineTo(width, topRadius)
        path.quadTo(width, 0f, width - topRadius, 0f)
        path.lineTo(topRadius, 0f)
        path.quadTo(0f, 0f, 0f, topRadius)
        path.close()
        return path
    }
}