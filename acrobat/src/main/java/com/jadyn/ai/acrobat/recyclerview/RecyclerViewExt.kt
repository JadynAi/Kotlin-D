package com.jadyn.ai.acrobat.recyclerview

import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.*
import kotlin.math.abs

/**
 *@version:
 *@FileDescription:RecyclerView扩展函数
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
fun RecyclerView.linearHor() {
    this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.linear() {
    this.layoutManager = LinearLayoutManager(this.context)
}

fun RecyclerView.grid(spanCount: Int) {
    this.layoutManager = GridLayoutManager(this.context, spanCount)
}

fun RecyclerView.tryClosetItemAnim() {
    (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
}

fun RecyclerView.smoothScrollToPositionSafe(pos: Int) {
    if ((pos < 0)) {
        return
    }
    smoothScrollToPosition(pos)
}

fun RecyclerView.Adapter<*>.notifyItemChangedSafe(vararg positions: Int) {
    if (positions.isNotEmpty()) {
        positions.forEach {
            notifyItemChangedSafe(it)
        }
    }
}

fun RecyclerView.Adapter<*>.notifyItemChangedSafePayloads(vararg positions: Pair<Int, Any>) {
    if (positions.isNotEmpty()) {
        positions.forEach {
            notifyItemChangedSafePayloads(it.first, it.second)
        }
    }
}

fun RecyclerView.Adapter<*>.notifyItemChangedSafe(pos: Int) {
    if (pos in 0 until itemCount) {
        notifyItemChanged(pos)
    }
}

fun RecyclerView.Adapter<*>.notifyItemChangedSafePayloads(pos: Int, pay: Any) {
    if (pos in 0 until itemCount) {
        notifyItemChanged(pos, pay)
    }
}

fun RecyclerView.smoothToScrollCenter(pos: Int) {
    layoutManager?.let {
        if (it.itemCount == 0) {
            return@let
        }
        if (pos < 0 || pos >= it.itemCount) {
            return@let
        }
        val scroller = CenterSmoothScroller(context)
        scroller.targetPosition = pos
        it.startSmoothScroll(scroller)
    }
}

/**
 * 横向滑动绑定一个尾部跟随滑动的view
 * @param offset view距离RecyclerView末端的距离
 * */
fun RecyclerView.bindHorTailView(view: View, offset: Int) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            recyclerView.layoutManager?.apply {
                this as LinearLayoutManager
                val position = findLastVisibleItemPosition()
                if (position == itemCount - 1) {
                    val v = findViewByPosition(position)
                    if (v == null) {
                        view.translationX = 0f
                    } else {
                        if (abs(v.right - width) >= offset) {
                            view.translationX = (v.right - width + offset).toFloat()
                        } else {
                            view.translationX = 0f
                        }
                    }
                } else {
                    view.translationX = 0f
                }
            }
        }
    })
}

class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) {
        // 计算距离
        val distance = distanceToCenter(layoutManager!!, targetView, getOrientationHelper(layoutManager!!)!!)
        // 计算动画时间;使用距离除以一个固定的速率，但是效果不是很明显。参考ios动画效果，给一个固定的默认时间
//        val time = ((abs(distance)) * targetView.context.resources.displayMetrics.densityDpi / 275f).toInt()
        val time = 350
        val scrollVertically = layoutManager!!.canScrollVertically()
        action.update(if (scrollVertically) 0 else distance, if (scrollVertically) distance else 0,
                time, AccelerateDecelerateInterpolator())
    }

    /**
     * 让item居中，那么Recyclerview滑动的距离，就是left到中线的距离，减去item 的width
     */
    private fun distanceToCenter(layoutManager: RecyclerView.LayoutManager, targetView: View,
                                 helper: OrientationHelper): Int {
//        val childCenter = helper.getDecoratedStart(targetView)+ helper.getDecoratedMeasurement(targetView) / 4
        val childCenter = targetView.left + targetView.width / 2
        val containerCenter = if (layoutManager.clipToPadding) {
            helper.startAfterPadding + helper.totalSpace / 2
        } else {
            helper.end / 2
        }
        return childCenter - containerCenter
    }

    /**
     * 不同方向上的距离使用不同的 OrientationHelper
     */
    private fun getOrientationHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (layoutManager.canScrollVertically()) {
            return OrientationHelper.createVerticalHelper(layoutManager)
        } else if (layoutManager.canScrollHorizontally()) {
            return OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return null
    }
}