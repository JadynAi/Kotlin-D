package com.jadyn.ai.kotlind.utils

import android.content.res.Resources
import android.graphics.Rect
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.jadyn.ai.kotlind.base.BaseApplication

/**
 *@version:
 *@FileDescription: 通用工具文件
 *@Author:jing
 *@Since:2018/6/20
 *@ChangeList:
 */

fun resources(): Resources = BaseApplication.instance.resources

val phonePixels: IntArray
    get() {
        val metrics = resources().displayMetrics
        return intArrayOf(metrics.widthPixels, metrics.heightPixels)
    }

val screenWidth by lazy { 
    phonePixels[0]
}

val screenHeight by lazy {
    phonePixels[1]
}

fun dip2px(dpValue: Float): Int {
    val scale = resources().displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun toastS(string: String) {
    Toast.makeText(BaseApplication.instance, string, Toast.LENGTH_SHORT).show()
}

fun getResColor(@ColorRes id: Int) = ContextCompat.getColor(BaseApplication.instance, id)

fun computeDisWithScreenW(denominator: Int, member: Int): Int {
    val ratio = denominator.toFloat() / member.toFloat()
    return (phonePixels[0] / ratio + 0.5f).toInt()
}

fun computeDisWithPhoneHeight(denominator: Int, member: Int): Int {
    val ratio = denominator.toFloat() / member.toFloat()
    return (phonePixels[1] / ratio + 0.5f).toInt()
}

/*
* window监听软键盘，返回listener对象，退出界面时用于remove
*
*  keyHeightCallBack：拿到疑似键盘高度时回调
* */
fun Window.addKeyBoardWatcher(keyHeightCallBack: (keyHeight: Int) -> Unit,
                              keyBoardVisible: (visible: Boolean) -> Unit): ViewTreeObserver.OnGlobalLayoutListener {
    if (attributes.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING) {
        throw IllegalArgumentException("adjust nothing can note get soft board height ")
    }

    var maxH = 0
    var keyH = 0
    val rect = Rect()
    var lastRecord = 0
    val listener = ViewTreeObserver.OnGlobalLayoutListener {
        rect.setEmpty()
        decorView.getWindowVisibleDisplayFrame(rect)
        if (rect.bottom != 0 && maxH == 0) {
            // 得到最开始的高度峰值
            maxH = rect.bottom
            lastRecord = maxH
        }
        //如果高度差大于最高差的1/3，则认为这部分差值为软键盘高度
        if (keyH == 0 && (maxH != 0 && (Math.abs(rect.bottom - maxH)) >= (maxH / 3))) {
            keyH = Math.abs(maxH - rect.bottom)
            keyHeightCallBack.invoke(keyH)
        }
        //当二者差大于等于键盘差，才调用键盘隐藏显示回调
        if ((lastRecord != 0 && keyH != 0) && (Math.abs(rect.bottom - lastRecord) >= keyH)) {
            keyBoardVisible.invoke(rect.bottom < lastRecord)
            lastRecord = rect.bottom
        }
    }
    decorView.viewTreeObserver.addOnGlobalLayoutListener(listener)
    return listener
}