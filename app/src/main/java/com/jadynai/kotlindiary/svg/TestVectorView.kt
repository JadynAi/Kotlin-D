package com.jadynai.kotlindiary.svg

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Trace
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jadynai.kotlindiary.R
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
        val t = measureTimeMillis {
            val drawable = resources.getDrawable(R.drawable.ic_arrow)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
        }
        Log.d("TestSvgView", "onDraw: cost $t")
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

class DashboardView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    val db by lazy { DashboardStartDrawable().apply { 
        callback = object :Drawable.Callback{
            override fun invalidateDrawable(who: Drawable) {
                invalidate()
            }

            override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
            }

            override fun unscheduleDrawable(who: Drawable, what: Runnable) {
            }
        }
    } }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        db.draw(canvas)
    }
}
