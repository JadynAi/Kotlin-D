package com.jadynai.kotlindiary.view.path

import android.animation.ValueAnimator
import android.graphics.Path
import android.util.Log
import android.view.animation.LinearInterpolator

/**
 *Jairett since 2021/12/23
 */
class PathTransition(vararg vectorPaths: String,
                     private val pathCallback: (curProgress: Float, path: Path) -> Unit) {

    private val pathData by lazy { Path() }
    private var isReverse = false
    var duration: Long = 800
        set(value) {
            field = value
            engine.duration = value
        }

    var progress: Float = 0f
        set(value) {
            require(value in 0f..1f)
            isReverse = false
            field = value
            val total = duration
            val curPlayTime = (value * total).toLong()
            Log.d("PathTransition", "setProgress: $value playTime: $curPlayTime")
            engine.currentPlayTime = curPlayTime
        }

    private val engine = ValueAnimator.ofObject(PathEvaluator(), *vectorPaths.map { p ->
        PathParserCompat.createNodesFromPathData(p)
    }.toTypedArray()).setDuration(duration).apply {
        interpolator = LinearInterpolator()
        addUpdateListener { va ->
            val nodes = va.animatedValue as Array<PathDataNode>
            PathUtils.setPathDataNodes(pathData, nodes)
            val p = (currentPlayTime.toFloat() / duration).coerceAtLeast(0f)
                .coerceAtMost(1f)
            val pv = if (isReverse) (1 - p) else p
            Log.d("PathTransition", "p change: $pv")
            pathCallback.invoke(pv, pathData)
        }
    }

    fun start() {
        isReverse = false
        engine.start()
    }

    fun reverse() {
        isReverse = true
        engine.reverse()
    }
}