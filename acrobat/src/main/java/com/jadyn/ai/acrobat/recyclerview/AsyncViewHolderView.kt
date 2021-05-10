package com.jadyn.ai.acrobat.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Size
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.jadyn.ai.kotlind.function.asyncview.tryForceSetCurThreadMainLooper
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 *JadynAi since 2021/4/30
 */
@SuppressLint("ViewConstructor")
class AsyncViewHolderView @JvmOverloads constructor(
        context: Context,
        targetResId: Int,
        private val targetRatio: SizeF,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        val viewAsyncExecutors by lazy {
            Executors.newSingleThreadExecutor(object : ThreadFactory {
                private val group by lazy {
                    System.getSecurityManager()?.threadGroup ?: Thread.currentThread().threadGroup
                }
                private val threadNum = AtomicInteger(1)

                override fun newThread(r: Runnable?): Thread {
                    val t = Thread(group, r, "AsyncView-${threadNum.getAndIncrement()}", 0)
                    if (t.isDaemon) t.isDaemon = false
                    if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
                    return t
                }
            })
        }
    }

    var afterTargetViewInflated: ((targetView: View) -> Unit)? = null

    private val recyclerViewLayoutParams
        get() = layoutParams as? RecyclerView.LayoutParams

    init {
        viewAsyncExecutors.execute {
            tryForceSetCurThreadMainLooper()
            val targetView = LayoutInflater.from(context).inflate(targetResId, null, false)
            post {
                addView(targetView, width, height)
                afterTargetViewInflated?.invoke(targetView)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        recyclerViewLayoutParams?.let {
            if (it.width == RecyclerView.LayoutParams.MATCH_PARENT) {
                // 垂直滑动的
                setMeasuredDimension(measuredWidth, (measuredWidth * (targetRatio.height / targetRatio.width)).toInt())
            } else {
                setMeasuredDimension((measuredHeight * (targetRatio.width / targetRatio.height)).toInt(), measuredHeight)
            }
        }
    }

    inline fun doWhenTargetViewInflated(crossinline callback: (targetView: View) -> Unit) {
        if ((childCount > 0)) {
            afterTargetViewInflated = null
            callback.invoke(getChildAt(0))
        } else {
            afterTargetViewInflated = { callback.invoke(it) }
        }
    }
}