package com.laihua.framework.ui.adapter.kotlinAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.laihua.framework.utils.ViewHolderHelper

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/7
 *@ChangeList:
 */
abstract class AbsItemViewK<D> {

    var dataHashCode: Int = 0

    lateinit var rootView: View

    lateinit var mHolderHelper: ViewHolderHelper

    lateinit var rAdapter: RAdapter<D>

    fun createViewHolder(ctx: Context, parent: ViewGroup?): RAdapter.ViewHolderK<D> {
        rootView = onCreateView(ctx, parent)
        mHolderHelper = ViewHolderHelper(rootView)
        onCreate(ctx, parent)
        return RAdapter.ViewHolderK(this)
    }

    abstract fun onCreateView(ctx: Context, parent: ViewGroup?): View

    abstract fun showItem(pos: Int, d: D?)

    open fun showItem(pos: Int, d: D?, payloads: MutableList<Any>){
        
    }

    open fun onCreate(ctx: Context, parent: ViewGroup?) {

    }

    fun isMatch(clazz: Class<*>) = clazz.hashCode() == dataHashCode
}