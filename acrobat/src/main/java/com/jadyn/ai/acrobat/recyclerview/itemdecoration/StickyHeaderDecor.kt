package com.jadyn.ai.acrobat.recyclerview.itemdecoration

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 *@version:
 *@FileDescription: 黏性头部Decoration
 *@Author:Jing
 *@Since:2019-09-21
 *@ChangeList:
 */
class StickyHeaderDecor(private val callback: (pos: Int) -> GroupInfo,
                        private val dividerH: Int = 0,
                        private val dividerIncludeEdge: Boolean = false,
                        private var headerHeight: Int = 0,
                        private val drawHeaderRect: (canvas: Canvas, info: GroupInfo,
                                                     l: Int, t: Int, r: Int, b: Int) -> Unit,
                        recyclerView: RecyclerView) : RecyclerView.ItemDecoration() {

    private var spanCount1: Int = 0

    init {
        recyclerView.layoutManager?.apply {
            if (this is GridLayoutManager) {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return callback.invoke(position).getSpanSize(this@apply)
                    }
                }
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val groupInfo = callback.invoke(position)
        if (groupInfo.isFirstViewInGroup(parent.layoutManager)) {
            outRect.top = headerHeight
        }
        val spanCount = getSpanCount(parent)
        val column = groupInfo.position % spanCount
        Log.d("StickyHeaderDecor", "getItemOffsets: ")
        if (dividerIncludeEdge) {
            if (spanCount != 0) {
                // 如果是LinearLayout就不用Left和Right
                outRect.left = dividerH - column * dividerH / spanCount
                outRect.right = (column + 1) * dividerH / spanCount
            }
            outRect.bottom = dividerH
        } else {
            if (spanCount != 0) {
                // 如果是LinearLayout就不用Left和Right
                outRect.left = column * dividerH / spanCount
                outRect.right = dividerH - (column + 1) * dividerH / spanCount
            }
            outRect.bottom = dividerH
        }
        parent.layoutManager?.apply {
            if (this is GridLayoutManager && groupInfo.isLast()) {
                val width = parent.width - (dividerH * (spanCount - if (dividerIncludeEdge) -1 else 1))
                view.layoutParams.width = width / spanCount
                view.layoutParams.height = width / spanCount
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        Log.d("StickyHeaderDecor", "onDrawOver: ")
        if (parent.childCount > 0) {
            val childCount = parent.childCount
            val paddingTop = parent.paddingTop
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            var top: Int
            for (i in 0 until childCount) {
                val view = parent.getChildAt(i)
                val index = parent.getChildAdapterPosition(view)
                val groupInfo = callback.invoke(index)
                //只有组内的第一个ItemView之上才绘制
                if (groupInfo.isFirst() && groupInfo.isFirstViewInGroup(parent.layoutManager)) {
                    top = view.top - headerHeight - paddingTop
                    drawHeaderRect(c, groupInfo, left, top, right, top + headerHeight)
                }
            }
            top = paddingTop
            val view = parent.getChildAt(0)
            val index = parent.getChildAdapterPosition(view)
            val groupInfo = callback.invoke(index)
            if (groupInfo.isLastViewInGroup(parent.layoutManager)) {
                // 最后一个item的底部
                val b = view.bottom - headerHeight + dividerH
                if (b <= top) {
                    top = b
                }
            }
            drawHeaderRect(c, groupInfo, left, top, right, top + headerHeight)
        }
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        if (spanCount1 > 0) {
            return spanCount1
        }
        // 列数
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount1 = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount1 = layoutManager.spanCount
        }
        return spanCount1
    }
}

class GroupInfo(val position: Int = 0,
                private val groupSize: Int = 0,
                val data: String) {

    fun isFirstViewInGroup(layoutManager: RecyclerView.LayoutManager?): Boolean {
        if (layoutManager is GridLayoutManager) {
            return position < layoutManager.spanCount
        }
        return isFirst()
    }

    fun isFirst() = position == 0

    fun isLastViewInGroup(layoutManager: RecyclerView.LayoutManager?): Boolean {
        if (layoutManager is GridLayoutManager) {
            return (groupSize - position) <= layoutManager.spanCount
        }
        return isLast()
    }

    fun isLast() = position == groupSize - 1 && position >= 0

    fun getSpanSize(layoutManager: GridLayoutManager): Int {
        if (isLast()) {
            return (layoutManager.spanCount - groupSize % layoutManager.spanCount) %
                    layoutManager.spanCount + 1
        }
        return 1
    }
}

class GroupItemData<D>(groupInfo: GroupInfo, data: D)