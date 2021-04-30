package com.jadyn.ai.kotlind.function.asyncview

import android.content.Context
import android.util.AttributeSet
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import java.util.concurrent.Executors

/**
 *JadynAi since 2021/4/30
 */
class AsyncView @JvmOverloads constructor(
        context: Context,
        resId: Int, private val targetSize: Size,
        viewGroup: ViewGroup,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        val viewAsyncExecutors by lazy { Executors.newSingleThreadExecutor() }
    }

    private var afterTargetViewInflated: (targetView: View) -> Unit = {}

    init {
        viewAsyncExecutors.execute {
            tryForceSetCurThreadMainLooper()
            val targetView = LayoutInflater.from(context).inflate(resId, viewGroup, false)
            post {
                addView(targetView, targetSize.width, targetSize.height)
                afterTargetViewInflated.invoke(targetView)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(targetSize.width, targetSize.height)
    }

    fun doWhenTargetViewInflated(callback: (targetView: View) -> Unit) {
        if ((childCount > 0)) {
            callback.invoke(getChildAt(0))
        } else {
            afterTargetViewInflated = callback
        }
    }
}