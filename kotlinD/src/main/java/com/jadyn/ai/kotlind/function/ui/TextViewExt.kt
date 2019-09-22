package com.jadyn.ai.kotlind.function.ui

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.jadyn.ai.kotlind.utils.toGBK

/**
 *@version:
 *@FileDescription:TextView扩展函数
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
fun TextView.drawable(textDrawable: TextDrawable.() -> Unit) {
    val d = TextDrawable()
    d.textDrawable()
    this.setCompoundDrawablesWithIntrinsicBounds(d.dl, d.dt, d.dr, d.db)
}

class TextDrawable(var dl: Drawable? = null, var dt: Drawable? = null, var dr: Drawable? = null, var db: Drawable? = null) {

    fun drawLeft(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int = -1,
                 drwable: (Int, Int) -> Drawable = ::getPressDrawable) {
        dl = drwable(normalRes, selectRes)
    }

    fun drawTop(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int = -1,
                drwable: (Int, Int) -> Drawable = ::getPressDrawable) {
        dt = drwable(normalRes, selectRes)
    }

    fun drawRight(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int = -1,
                  drwable: (Int, Int) -> Drawable = ::getPressDrawable) {
        dr = drwable(normalRes, selectRes)
    }

    fun drawBottom(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int = -1,
                   drwable: (Int, Int) -> Drawable = ::getPressDrawable) {
        db = drwable(normalRes, selectRes)
    }
}

fun TextView.pressTextColor(normalColor: Int, pressColor: Int) {
    val list = ColorStateList(arrayOf(intArrayOf(R.attr.state_pressed),
            intArrayOf()), intArrayOf(pressColor, normalColor))
    this.setTextColor(list)
}

fun TextView.selectTextColor(normalColor: Int, pressColor: Int) {
    val list = ColorStateList(arrayOf(intArrayOf(R.attr.state_selected),
            intArrayOf()), intArrayOf(pressColor, normalColor))
    this.setTextColor(list)
}

fun TextView.checkedTextColor(normalColor: Int, pressColor: Int) {
    val list = ColorStateList(arrayOf(intArrayOf(R.attr.state_checked),
            intArrayOf()), intArrayOf(pressColor, normalColor))
    this.setTextColor(list)
}

fun TextView.generateHexColor(): String {
//    var a = Integer.toHexString(Color.alpha(currentTextColor))
    var r = Integer.toHexString(Color.red(currentTextColor))
    var g = Integer.toHexString(Color.green(currentTextColor))
    var b = Integer.toHexString(Color.blue(currentTextColor))
//    a = if (a.length == 1) "0" + a else a
    r = if (r.length == 1) "0" + r else r
    g = if (g.length == 1) "0" + g else g
    b = if (b.length == 1) "0" + b else b
    return TextUtils.concat("#", r, g, b).toString()
}

fun EditText.generateAutoNewLineText(success: (String) -> Unit) {
    post {
        val s = text.toString().trim()
        if (s.isBlank()) {
            success("")
            return@post
        }
        val width = width - paddingLeft - paddingRight
        val list = s.split("\n".toRegex())
        val sb = StringBuilder()
        list.forEach {
            if (paint.measureText(it) >= width) {
                val sb1 = StringBuilder()
                //每个字符的宽度
                val sl = paint.measureText(it).toInt() / it.length
                //达到EditText的宽度需要多少字符
                val sc = width / sl
                it.forEachIndexed { index, c ->
                    if (index != 0 && index % sc == 0) {
                        sb1.append(c)
                        sb1.append("\n")
                    } else {
                        sb1.append(c)
                    }
                }
                sb.append(sb1)
            } else {
                sb.append(it)
            }
        }
        success(sb.toString())
    }
}

/*
 * 设置编辑框最大输入字数，标准是一个汉字=两个英文字符=2个计数
 * */
fun EditText.maxInputNum(num: Int) {
    filters = arrayOf(object : InputFilter {
        override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence {
            source?.apply {
                Log.d("maxInputNum", " source $this")
                val nowSize = this@maxInputNum.text.toString().toGBK().size
                if (nowSize + this.toString().toGBK().size > num) {
                    return ""
                }
                return this
            }
            return source ?: ""
        }
    })
}
