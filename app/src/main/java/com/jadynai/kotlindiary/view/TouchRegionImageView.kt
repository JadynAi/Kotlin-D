package com.jadynai.kotlindiary.view


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

/**
 *Jairett since 2021/12/22
 */
class TouchRegionImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var touchRegionPath: Path? = null
    private var touchRegion: Region? = null

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = Color.GREEN
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (touchRegionPath == null) {
            val arcPath = Path()
            arcPath.moveTo(0f, 0f)
            arcPath.quadTo((2 * width).toFloat(), height * 0.5f, 0f, height.toFloat())
            val pm = PathMeasure(arcPath, false)
            val segmentPath = Path()
            val length = pm.length
            val startRatio = 0.2f
            val endRatio = 0.7f
            val startD = startRatio * length
            val endD = endRatio * length
            pm.getSegment(startD, endD, segmentPath, true)
            val startPosArray = floatArrayOf(0f, 0f)
            val endPosArray = floatArrayOf(0f, 0f)
            pm.getPosTan(startD, startPosArray, null)
            pm.getPosTan(endD, endPosArray, null)
            val regionPath = Path()
            regionPath.moveTo(0f, startPosArray[1])
            regionPath.lineTo(startPosArray[0], startPosArray[1])
            regionPath.addPath(segmentPath)
            regionPath.lineTo(0f, endPosArray[1])
            regionPath.lineTo(0f, startPosArray[1])
            regionPath.close()
            val rectF = RectF()
            regionPath.computeBounds(rectF, true)
            val region = Region()
            region.setPath(regionPath, Region(rectF.left.toInt(), rectF.top.toInt(),
                rectF.right.toInt(), rectF.bottom.toInt()))
            touchRegion = region
            touchRegionPath = regionPath
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (touchRegion?.contains(event.x.toInt(), event.y.toInt()) == true) {
            return super.onTouchEvent(event)
        }
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        touchRegionPath?.let { region ->
            canvas?.drawPath(region, paint)
        }
    }
}