package com.motong.cm.kotlintest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/6
 *@ChangeList:
 */
abstract class AbsItemView<D> {

    abstract var layoutId: Int

    fun createViewHolder(ctx: Context, parent: ViewGroup): ViewHolderK {
        val view = LayoutInflater.from(ctx).inflate(layoutId, null)
        return ViewHolderK(view)
    }

    abstract fun showItem(pos: Int, d: D)
}