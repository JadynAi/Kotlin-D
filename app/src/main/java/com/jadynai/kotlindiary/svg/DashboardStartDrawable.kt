package com.jadynai.kotlindiary.svg

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.util.Log
import com.jadyn.ai.kotlind.utils.dp
import kotlin.system.measureTimeMillis

/**
 *Jairett since 2022/5/19
 */
class DashboardStartDrawable : ShapeDrawable() {
    private val matrix by lazy { Matrix() }
    private val path by lazy { android.graphics.Path() }

    private val firstLevelPathGradientColors: IntArray
        get() = intArrayOf(
            getCurrentColor(progress, Color.parseColor("#FFBDBECE"), Color.parseColor("#FF96A3BD")),
            getCurrentColor(progress, Color.parseColor("#FFD5CFDC"), Color.parseColor("#FF96A3BD")),
            getCurrentColor(progress, Color.parseColor("#FFC9CDDA"), Color.parseColor("#FFE9B7CF")),
            getCurrentColor(progress, Color.parseColor("#FFB7C3D3"), Color.parseColor("#FFE9B7CF")),
            getCurrentColor(progress, Color.parseColor("#FF94A0B9"), Color.parseColor("#FFDDE3EF")),
            getCurrentColor(progress, Color.parseColor("#FF96A3BD"), Color.parseColor("#FFD3D9E6"))
        )
    private val secondLevelPathGradientColors: IntArray
        get() = intArrayOf(
            getCurrentColor(progress, Color.parseColor("#00000000"), Color.parseColor("#FFCDCAD8")),
            getCurrentColor(progress, Color.parseColor("#00000000"), Color.parseColor("#00CDCAD8"))
        )
    private val thirdLevelPathGradientColors: IntArray
        get() = intArrayOf(
            getCurrentColor(progress, Color.parseColor("#00000000"), Color.parseColor("#FFB5BFD2")),
            getCurrentColor(progress, Color.parseColor("#00000000"), Color.parseColor("#00D4D4DF"))
        )

    private val firstLevelGradientPositions: FloatArray
        get() = floatArrayOf(0f,
            getCurrentNum(progress, 0.140925f, 0.0001f),
            getCurrentNum(progress, 0.230471f, 0.350484f),
            getCurrentNum(progress, 0.537202f, 0.351f),
            getCurrentNum(progress, 0.794276f, 0.732712f),
            getCurrentNum(progress, 0.991628f, 0.887799f)
        )
    private val secondLevelGradientPositions: FloatArray
        get() = floatArrayOf(
            getCurrentNum(progress, 1f, 0.187587f),
            getCurrentNum(progress, 1f, 0.642293f)
        )
    private val thirdLevelGradientPositions: FloatArray
        get() = floatArrayOf(
            getCurrentNum(progress, 1f, 0.468125f),
            getCurrentNum(progress, 1f, 0.642293f)
        )

    init {
        intrinsicWidth = getCurrentNum(0f, 131f.dp.toFloat(), 105f.dp.toFloat()).toInt()
        intrinsicHeight = 151f.dp
    }

    var progress: Float = 0f
        set(value) {
            val v = value.coerceAtMost(1f).coerceAtLeast(0f)
            Log.d("DashboardStartDrawable", " progress v: $v")
            field = v
            intrinsicWidth = getCurrentNum(v, 131f.dp.toFloat(), 105f.dp.toFloat()).toInt()
        }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val cost = measureTimeMillis {

            val width = intrinsicWidth
            val height = intrinsicHeight
            if (width == 0 || height == 0) {
                return
            }
            val fl = getCurrentNum(progress, 131f, 105f)
            val scaleX: Float = width / fl
            val scaleY: Float = height / 151.0f
            matrix.reset()
            matrix.setValues(floatArrayOf(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f))
            matrix.setScale(scaleX, scaleY,0f,0f)
            path.moveTo(0.0f, 0.0f)
            path.lineTo(0.0f, 151.0f)
            path.lineTo(fl, 151.0f)
            path.lineTo(fl, 0.0f)
            path.transform(matrix)
            path.close()
            paint.alpha = 255
            paint.shader = LinearGradient(
                getCurrentNum(progress, 2.32258f, -121.333f),
                getCurrentNum(progress, 2.41128f, 214.064f),
                getCurrentNum(progress, 152.6f, 35.0434f),
                getCurrentNum(progress, 123.126f, 236.31f),
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

    private fun getCurrentNum(fraction: Float, start: Float, end: Float): Float {
        return start + (end - start) * fraction
    }

    private fun getCurrentColor(fraction: Float, startColor: Int, endColor: Int): Int {
        val redCurrent: Int
        val blueCurrent: Int
        val greenCurrent: Int
        val alphaCurrent: Int
        val redStart = Color.red(startColor)
        val blueStart = Color.blue(startColor)
        val greenStart = Color.green(startColor)
        val alphaStart = Color.alpha(startColor)
        val redEnd = Color.red(endColor)
        val blueEnd = Color.blue(endColor)
        val greenEnd = Color.green(endColor)
        val alphaEnd = Color.alpha(endColor)
        val redDifference = redEnd - redStart
        val blueDifference = blueEnd - blueStart
        val greenDifference = greenEnd - greenStart
        val alphaDifference = alphaEnd - alphaStart
        redCurrent = (redStart + fraction * redDifference).toInt()
        blueCurrent = (blueStart + fraction * blueDifference).toInt()
        greenCurrent = (greenStart + fraction * greenDifference).toInt()
        alphaCurrent = (alphaStart + fraction * alphaDifference).toInt()
        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent)
    }
}