package com.jadynai.kotlindiary.canvas

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-08-15
 *@ChangeList:
 */
class CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply { 
        }
    }
}