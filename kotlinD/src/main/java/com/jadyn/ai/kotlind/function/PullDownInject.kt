package com.jadyn.ai.kotlind.function

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.jadyn.ai.kotlind.utils.screenHeight

/**
 *@version:
 *@FileDescription: Activity 添加下拉手势
 *@Author:jing
 *@Since:2018/12/11
 *@ChangeList:
 */
class PullDownInject private constructor(activity: AppCompatActivity) {

    private val TAG = "PullDownInject"

    var pullDownFunction: (() -> Unit)? = null

    private val max_distance = screenHeight * 0.1f

    companion object {

        fun inject(activity: AppCompatActivity, pullDown: (() -> Unit)? = null): PullDownInject {
            val inject = PullDownInject(activity)
            inject.pullDownFunction = pullDown
            return inject
        }
    }

    private var y = 0f

    private val gestureDetector by lazy {
        GestureDetectorCompat(activity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                Log.d(TAG, "onScroll $distanceY : ")
                y += distanceY
                val judge = y < 0 && (Math.abs(y) >= max_distance)
                if (judge) {
                    pullDownFunction?.invoke()
                }
                return judge
            }
        })
    }

    init {
        if (activity.isFinishing) {
            // 2018/12/11-16:04 Do Nothing
        } else {
            val frameLayout = activity.findViewById<ViewGroup>(android.R.id.content)
            val realLayout = frameLayout.getChildAt(0)
            frameLayout.removeView(realLayout)
            val pullLayout = PullView(activity)
            frameLayout.stopNestedScroll()
            pullLayout.stopNestedScroll()
            realLayout.stopNestedScroll()
            pullLayout.addView(realLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT))
            frameLayout.addView(pullLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT))
        }
    }

    inner class PullView : FrameLayout {

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
        constructor(context: Context) : super(context)
        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

        override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
            gestureDetector.onTouchEvent(ev)
            return super.dispatchTouchEvent(ev)
        }

        override fun scrollBy(x: Int, y: Int) {

        }

        override fun scrollTo(x: Int, y: Int) {

        }

        override fun canScrollVertically(direction: Int): Boolean {
            return false
        }
    }
}