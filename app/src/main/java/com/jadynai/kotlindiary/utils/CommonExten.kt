package com.jadynai.kotlindiary.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jadynai.cm.kotlintest.R
import com.jadynai.kotlindiary.base.BaseApplication
import kotlinx.android.synthetic.main.dialog_diary.view.*

/**
 *@version:
 *@FileDescription: 通用工具文件
 *@Author:jing
 *@Since:2018/6/20
 *@ChangeList:
 */

private val MIN_WVGA_HEIGHT = 700
private val WVGA_HEIGHT = 800
private val MIN_HD_HEIGHT = 1180
private val HD_HEIGHT = 1280

fun resources(): Resources = BaseApplication.instance.resources

val phonePixels: IntArray
    get() {
        val metrics = resources().displayMetrics
        val curWidth = metrics.widthPixels
        var curHeight = metrics.heightPixels
        if (curHeight in MIN_WVGA_HEIGHT..WVGA_HEIGHT) {
            curHeight = WVGA_HEIGHT
        }
        if (curHeight in MIN_HD_HEIGHT..HD_HEIGHT) {
            curHeight = HD_HEIGHT
        }
        return intArrayOf(curWidth, curHeight)
    }

fun dip2px(dpValue: Float): Int {
    val scale = resources().displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun toastS(string: String) {
    Toast.makeText(BaseApplication.instance, string, Toast.LENGTH_SHORT).show()
}

fun getResColor(@ColorRes id: Int) = ContextCompat.getColor(BaseApplication.instance, id)

fun getResDrawable(@DrawableRes id: Int) = ContextCompat.getDrawable(BaseApplication.instance, id)

fun getS(@StringRes id: Int): String {
    return try {
        BaseApplication.instance.getString(id)
    } catch (e: Exception) {
        ""
    }
}

fun getS(@StringRes id: Int, vararg formatArgs: Any): String {
    return try {
        BaseApplication.instance.getString(id, *formatArgs)
    } catch (e: Exception) {
        ""
    }
}

fun computeDisWithScreenW(denominator: Int, member: Int): Int {
    val ratio = denominator.toFloat() / member.toFloat()
    return (phonePixels[0] / ratio + 0.5f).toInt()
}

fun computeDisWithPhoneHeight(denominator: Int, member: Int): Int {
    val ratio = denominator.toFloat() / member.toFloat()
    return (phonePixels[1] / ratio + 0.5f).toInt()
}

inline fun commonDialog(ctx: Context, title: String = "", init: AlertBuilder.() -> Unit): AlertBuilder {
    return AlertBuilder(ctx, title).apply(init)
}

class AlertBuilder(private val ctx: Context,
                   private val title: String = "",
                   private inline var okClick: (View) -> Unit = { },
                   private inline var noClick: (View) -> Unit = { }) {

    private val dialog by lazy {
        Dialog(ctx)
    }

    private var view: View

    init {
        view = LayoutInflater.from(ctx).inflate(R.layout.dialog_diary, null)
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        dialog.setContentView(view, ViewGroup.LayoutParams(600, 600))
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        view.dialog_title_tv.text = title
        view.dialog_sure_tv.setOnClickListener {
            okClick(it)
            dialog.dismiss()
        }
        view.dialog_cancel_tv.setOnClickListener {
            noClick(it)
            dialog.dismiss()
        }
    }

    fun okButton(bt: String = "确定", click: (View) -> Unit) {
        view.dialog_sure_tv.text = bt
        okClick = click
    }

    fun noButton(bt: String = "取消", click: (View) -> Unit) {
        view.dialog_cancel_tv.text = bt
        noClick = click
    }

    fun dismissListener(handler: (DialogInterface) -> Unit) {
        dialog.setOnDismissListener(handler)
    }

    fun show() {
        dialog.show()
    }
}