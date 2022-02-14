package com.jadynai.kotlindiary.svg

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Trace
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.BitmapCompat
import com.github.megatronking.svg.support.SVGDrawable
import com.jadyn.ai.kotlind.function.ui.getResDrawable
import com.jadynai.kotlindiary.R
import com.jadynai.svg.sample.drawables.ic_arrow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

/**
 *Jairett since 2022/1/18
 */
class TestVectorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Trace.beginSection("SVGStartTraceL")
        val t = measureTimeMillis {
            val drawable = resources.getDrawable(R.drawable.ic_arrow)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
        }
        Log.d("TestSvgView", "onDraw: cost $t")
        Trace.endSection()
    }
}

class TestPngView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Trace.beginSection("PNGStartTraceL")
        val t = measureTimeMillis {
            val drawable = resources.getDrawable(R.drawable.main_separator_light_bg_webp)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
        }
        Log.d("TestPngView", "onDraw: cost $t")
        Trace.endSection()
    }
}