package com.jadynai.kotlindiary.svg

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.util.Log
import kotlin.system.measureTimeMillis

/**
 *Jairett since 2022/5/19
 */
class DashboardStartDrawable : ShapeDrawable() {
    private val matrix by lazy { Matrix() }
    private val path by lazy { android.graphics.Path() }

    private val firstLevelPathGradientColors by lazy {
        intArrayOf(Color.parseColor("#FFBDBECE"),
            Color.parseColor("#FFD5CFDC"),
            Color.parseColor("#FFC9CDDA"),
            Color.parseColor("#FFB7C3D3"),
            Color.parseColor("#FF94A0B9"),
            Color.parseColor("#FF96A3BD")
        )
    }
    private val secondLevelPathGradientColors by lazy {
        intArrayOf(
            Color.parseColor("#00000000"),
            Color.parseColor("#00000000"),
        )
    }
    private val thirdLevelPathGradientColors by lazy {
        intArrayOf(
            Color.parseColor("#00000000"),
            Color.parseColor("#00000000"),
        )
    }

    private val firstLevelGradientPositions by lazy {
        floatArrayOf(0f, 0.140925f, 0.230471f, 0.537202f, 0.794276f, 0.991628f)
    }
    private val secondLevelGradientPositions by lazy {
        floatArrayOf(1f, 1f)
    }
    private val thirdLevelGradientPositions by lazy {
        floatArrayOf(1f, 1f)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val cost = measureTimeMillis {

            val width = intrinsicWidth
            val height = intrinsicHeight
            if (width == 0 || height == 0) {
                return
            }
            val scaleX: Float = width / 131.0f
            val scaleY: Float = height / 151.0f
            matrix.setValues(floatArrayOf(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f))
            matrix.postScale(scaleX, scaleY)
            path.moveTo(0.0f, 0.0f)
            path.lineTo(0.0f, 151.0f)
            path.lineTo(131.0f, 151.0f)
            path.lineTo(131.0f, 0.0f)
            path.transform(matrix)
            path.close()
            paint.shader = LinearGradient(2.32258f, 2.41128f, 152.6f, 123.126f,
                firstLevelPathGradientColors, firstLevelGradientPositions, Shader.TileMode.CLAMP)
                .apply { setLocalMatrix(matrix) }
            canvas.drawPath(path, paint)

            path.reset()

            path.moveTo(52.0f, 100.0f)
            path.rMoveTo(46.409f, 0.0f)
            path.cubicTo(98.454605f, 90.17159f, 95.37687f, 80.57824f, 89.62197f, 72.610756f)
            path.cubicTo(83.86708f, 64.643265f, 75.72727f, 58.70623f, 66.38262f, 55.66038f)
            path.cubicTo(57.03798f, 52.61453f, 46.963013f, 52.61453f, 37.618366f, 55.66038f)
            path.cubicTo(28.27372f, 58.70623f, 20.13391f, 64.643265f, 14.379017f, 72.610756f)
            path.cubicTo(8.6241255f, 80.57824f, 5.546385f, 90.17159f, 5.5919952f, 100.0f)
            path.cubicTo(5.546385f, 109.82841f, 8.6241255f, 119.42176f, 14.379017f, 127.389244f)
            path.cubicTo(20.13391f, 135.35674f, 28.27372f, 141.29376f, 37.618366f, 144.33961f)
            path.cubicTo(46.963013f, 147.38547f, 57.03798f, 147.38547f, 66.38262f, 144.33961f)
            path.cubicTo(75.72727f, 141.29376f, 83.86708f, 135.35674f, 89.62197f, 127.389244f)
            path.cubicTo(95.37687f, 119.42176f, 98.454605f, 109.82841f, 98.409f, 100.0f)
            path.transform(matrix)

            paint.shader = LinearGradient(-31.032202f, 251.5269f, 13.419399f, 110.623604f,
                secondLevelPathGradientColors, secondLevelGradientPositions, Shader.TileMode.CLAMP)
                .apply { setLocalMatrix(matrix) }
            paint.alpha = 90
            canvas.drawPath(path, paint)
            path.reset()

            path.moveTo(52.0f, 100.0f)
            path.rMoveTo(46.409f, -0.0f)
            path.cubicTo(98.454605f, 109.82841f, 95.37687f, 119.42176f, 89.62197f, 127.389244f)
            path.cubicTo(83.86708f, 135.35674f, 75.72727f, 141.29376f, 66.38262f, 144.33961f)
            path.cubicTo(57.03798f, 147.38547f, 46.963013f, 147.38547f, 37.618366f, 144.33961f)
            path.cubicTo(28.27372f, 141.29376f, 20.13391f, 135.35674f, 14.379017f, 127.389244f)
            path.cubicTo(8.6241255f, 119.42176f, 5.546385f, 109.82841f, 5.5919952f, 100.0f)
            path.cubicTo(5.546385f, 90.17159f, 8.6241255f, 80.57824f, 14.379017f, 72.610756f)
            path.cubicTo(20.13391f, 64.643265f, 28.27372f, 58.70623f, 37.618366f, 55.66038f)
            path.cubicTo(46.963013f, 52.61453f, 57.03798f, 52.61453f, 66.38262f, 55.66038f)
            path.cubicTo(75.72727f, 58.70623f, 83.86708f, 64.643265f, 89.62197f, 72.610756f)
            path.cubicTo(95.37687f, 80.57824f, 98.454605f, 90.17159f, 98.409f, 100.0f)
            path.transform(matrix)

            paint.shader = LinearGradient(-58.7098f, 85.7416f, 72.6883f, 113.6987f,
                thirdLevelPathGradientColors, thirdLevelGradientPositions, Shader.TileMode.CLAMP)
                .apply { setLocalMatrix(matrix) }
            canvas.drawPath(path, paint)
            path.reset()
        }
        Log.d("TimeTest", "cost: $cost")
    }

    protected fun applyAlpha(color: Int, alpha: Float): Int {
        var c = color
        val alphaBytes = Color.alpha(c)
        c = c and 0x00FFFFFF
        c = c or ((alphaBytes * alpha).toInt() shl 24)
        return c
    }
}