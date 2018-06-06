package com.motong.cm.kotlintest.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/6
 *@ChangeList:
 */
fun <T> ViewHolder<T>.setVisibility(visibility: Int, vararg viewIds: Int): ViewHolder<T> {
    viewIds.forEach {
        view<View>(it).visibility = visibility
    }
    return this
}

fun <T> ViewHolder<T>.setVisible(visible: Boolean, vararg viewIds: Int): ViewHolder<T> {
    setVisibility(if (visible) View.VISIBLE else View.GONE, *viewIds)
    return this
}

fun <T> ViewHolder<T>.setSelected(viewId: Int, isSelected: Boolean): ViewHolder<T> {
    view<View>(viewId).isSelected = isSelected
    return this
}

fun <T> ViewHolder<T>.setText(viewId: Int, charSequence: CharSequence): ViewHolder<T> {
    view<TextView>(viewId).text = charSequence
    return this
}

fun <T> ViewHolder<T>.setText(viewId: Int, resId: Int): ViewHolder<T> {
    view<TextView>(viewId).setText(resId)
    return this
}

fun <T> ViewHolder<T>.setImageResource(viewId: Int, resId: Int): ViewHolder<T> {
    view<ImageView>(viewId).setImageResource(resId)
    return this
}

fun <T> ViewHolder<T>.setOnClickListener(listener: View.OnClickListener, vararg ids: Int): ViewHolder<T> {
    ids.forEach {
        view<View>(it).setOnClickListener(listener)
    }
    return this
}

fun <T> ViewHolder<T>.getItem(action: (item: T) -> Unit) {
    val pos = adapterPosition
    if (pos >= 0) {
        action(adapter.data[pos])
    }
}

private fun <T> ViewHolder<T>.getItemB(action: (item: T) -> Boolean): Boolean {
    val pos = adapterPosition
    return if (pos >= 0) {
        action(adapter.data[pos])
    } else {
        false
    }
}

fun <T> ViewHolder<T>.onClick(view: View, action: (item: T) -> Unit) {
    view.setOnClickListener {
        getItem(action)
    }
}

fun <T> ViewHolder<T>.onLongClick(view: View, action: (item: T) -> Boolean) {
    view.setOnLongClickListener {
        getItemB(action)
    }
}
