package com.jadynai.kotlindiary.svg

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.drawable.ShapeDrawable

/**
 *Jairett since 2022/5/19
 */
class TestSvgDrawable : ShapeDrawable() {
    private val path by lazy { Path() }

    override fun draw(canvas: Canvas) {
        path.reset()
        path.moveTo(0.0f, 0.0f)
        path.lineTo(0.0f, 151.0f)
        path.lineTo(131.0f, 151.0f)
        path.lineTo(131.0f, 0.0f)
        path.close()
        path.moveTo(0.0f, 0.0f)
//        canvas.drawPath(path, null)
    }
}