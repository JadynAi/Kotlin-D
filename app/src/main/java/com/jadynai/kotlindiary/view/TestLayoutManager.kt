package com.jadynai.kotlindiary.view


/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-08-15
 *@ChangeList:
 */
//class TestLayoutManager : RecyclerView.LayoutManager() {
//    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
//        return RecyclerView.LayoutParams(
//                RecyclerView.LayoutParams.MATCH_PARENT,
//                RecyclerView.LayoutParams.MATCH_PARENT
//        )
//    }
//
//    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
//        super.onLayoutChildren(recycler, state)
//        Log.d(this.javaClass.name, "onLayoutChildren : ")
//        recycler?.apply {
//            detachAndScrapAttachedViews(this)
//            if (itemCount == 0) {
//                return@apply
//            }
//            val view = getViewForPosition(0)
//            measureChildWithMargins(view, 0, 0)
//            val itemW = getDecoratedMeasuredWidth(view)
//            val itemH = getDecoratedMeasuredHeight(view)
//
//        }
//    }
//
//    override fun canScrollVertically(): Boolean {
//        return true
//    }
//
//    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
//        Log.d(this.javaClass.name, "scrollVerticallyBy : $dy")
//        offsetChildrenVertical(dy)
//        return super.scrollVerticallyBy(dy, recycler, state)
//    }
//
//}
//
//class CuManager : RecyclerView.LayoutManager() {
//    private var mSumDy = 0
//    private var mTotalHeight = 0
//
//    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
//        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
//                RecyclerView.LayoutParams.WRAP_CONTENT)
//    }
//
//    private var mItemWidth: Int = 0
//    private var mItemHeight:Int = 0
//    private val mItemRects = SparseArray<Rect>()
//
//    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
//        Log.d(this.javaClass.name, "onLayoutChildren: ")
//        if (itemCount == 0) {//没有Item，界面空着吧
//            detachAndScrapAttachedViews(recycler)
//            return
//        }
//        detachAndScrapAttachedViews(recycler)
//
//        //将item的位置存储起来
//        val childView = recycler!!.getViewForPosition(0)
//        measureChildWithMargins(childView, 0, 0)
//        mItemWidth = getDecoratedMeasuredWidth(childView)
//        mItemHeight = getDecoratedMeasuredHeight(childView)
//
//        val visibleCount = getVerticalSpace() / mItemHeight
//
//
//        //定义竖直方向的偏移量
//        var offsetY = 0
//
//        for (i in 0 until itemCount) {
//            val rect = Rect(0, offsetY, mItemWidth, offsetY + mItemHeight)
//            mItemRects.put(i, rect)
//            offsetY += mItemHeight
//        }
//
//
//        for (i in 0 until visibleCount) {
//            val rect = mItemRects.get(i)
//            val view = recycler.getViewForPosition(i)
//            addView(view)
//            //addView后一定要measure，先measure再layout
//            measureChildWithMargins(view, 0, 0)
//            layoutDecorated(view, rect.left, rect.top, rect.right, rect.bottom)
//        }
//
//        //如果所有子View的高度和没有填满RecyclerView的高度，
//        // 则将高度设置为RecyclerView的高度
//        mTotalHeight = Math.max(offsetY, getVerticalSpace())
//    }
//
//    private fun getVerticalSpace(): Int {
//        return height - paddingBottom - paddingTop
//    }
//
//    override fun canScrollVertically(): Boolean {
//        return true
//    }
//
//    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
//        Log.d(this.javaClass.name, "scrollVerticallyBy: ")
//        if (childCount <= 0) {
//            return dy
//        }
//
//        var travel = dy
//        //如果滑动到最顶部
//        if (mSumDy + dy < 0) {
//            travel = -mSumDy
//        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {
//            //如果滑动到最底部
//            travel = mTotalHeight - getVerticalSpace() - mSumDy
//        }
//
//        //回收越界子View
//        for (i in childCount - 1 downTo 0) {
//            val child = getChildAt(i)
//            if (travel > 0) {//需要回收当前屏幕，上越界的View
//                if (getDecoratedBottom(child) - travel < 0) {
//                    removeAndRecycleView(child, recycler!!)
//                    continue
//                }
//            } else if (travel < 0) {//回收当前屏幕，下越界的View
//                if (getDecoratedTop(child) - travel > height - paddingBottom) {
//                    removeAndRecycleView(child, recycler!!)
//                    continue
//                }
//            }
//        }
//
//        val visibleRect = getVisibleArea(travel)
//        //布局子View阶段
//        if (travel >= 0) {
//            val lastView = getChildAt(childCount - 1)
//            val minPos = getPosition(lastView) + 1//从最后一个View+1开始吧
//
//            //顺序addChildView
//            for (i in minPos until itemCount) {
//                val rect = mItemRects.get(i)
//                if (Rect.intersects(visibleRect, rect)) {
//                    val child = recycler!!.getViewForPosition(i)
//                    addView(child)
//                    measureChildWithMargins(child, 0, 0)
//                    layoutDecorated(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy)
//                } else {
//                    break
//                }
//            }
//        } else {
//            val firstView = getChildAt(0)
//            val maxPos = getPosition(firstView) - 1
//
//            for (i in maxPos downTo 0) {
//                val rect = mItemRects.get(i)
//                if (Rect.intersects(visibleRect, rect)) {
//                    val child = recycler!!.getViewForPosition(i)
//                    addView(child, 0)//将View添加至RecyclerView中，childIndex为1，但是View的位置还是由layout的位置决定
//                    measureChildWithMargins(child, 0, 0)
//                    layoutDecoratedWithMargins(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy)
//                } else {
//                    break
//                }
//            }
//        }
//
//        mSumDy += travel
//        // 平移容器内的item
//        offsetChildrenVertical(-travel)
//        return travel
//    }
//
//
//    /**
//     * 获取可见的区域Rect
//     *
//     * @return
//     */
//    private fun getVisibleArea(dy: Int): Rect {
//        return Rect(paddingLeft, paddingTop + mSumDy + dy, width + paddingRight, getVerticalSpace() + mSumDy + dy)
//    }
//}