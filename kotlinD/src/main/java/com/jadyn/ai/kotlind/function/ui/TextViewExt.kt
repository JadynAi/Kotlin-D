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

    fun drawLeft(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int = -1) {
        dl = getPressDrawable(normalRes, selectRes)
    }

    fun drawTop(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int = -1) {
        dt = getPressDrawable(normalRes, selectRes)
    }

    fun drawRight(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int = -1) {
        dr = getPressDrawable(normalRes, selectRes)
    }

    fun drawBottom(@DrawableRes normalRes: Int, @DrawableRes selectRes: Int = -1) {
        db = getPressDrawable(normalRes, selectRes)
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
    val a = Integer.toHexString(Color.alpha(currentTextColor))
    val r = Integer.toHexString(Color.red(currentTextColor))
    val g = Integer.toHexString(Color.green(currentTextColor))
    val b = Integer.toHexString(Color.blue(currentTextColor))
    return TextUtils.concat("#", a, r, g, b).toString()
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
