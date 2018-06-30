package com.jadynai.kotlindiary

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jadynai.cm.kotlintest.R
import kotlinx.android.synthetic.main.dialog_diary.view.*

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/20
 *@ChangeList:
 */
inline fun <T> T.toastS(ctx: Context, string: String) {
    Toast.makeText(ctx, string, Toast.LENGTH_SHORT).show()
}

fun <T> T.askDialog(ctx: Context, title: String = "", init: AlertBuilder.() -> Unit): AlertBuilder {
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