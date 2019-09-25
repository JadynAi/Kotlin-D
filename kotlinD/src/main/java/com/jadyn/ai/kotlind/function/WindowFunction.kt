package com.jadyn.ai.kotlind.function

import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-09-22
 *@ChangeList:
 */
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