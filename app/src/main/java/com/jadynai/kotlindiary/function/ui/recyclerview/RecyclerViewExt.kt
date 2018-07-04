package com.jadynai.kotlindiary.function.ui.recyclerview

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 *@version:
 *@FileDescription:RecyclerView扩展函数
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
inline fun RecyclerView.linearHor() {
    this.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
}

inline fun RecyclerView.linear() {
    this.layoutManager = LinearLayoutManager(this.context)
}

inline fun RecyclerView.grid(spanCount: Int) {
    this.layoutManager = GridLayoutManager(this.context, spanCount)
}