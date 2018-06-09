package com.laihua.framework.ui.adapter.kotlinAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/7
 *@ChangeList:
 */
abstract class AbsItemViewK<in D> :LayoutContainer{

    override val containerView: View?
        get() = rootView

    var dataHashCode: Int = 0

    private lateinit  var rootView: View
    
    abstract fun onCreateView(ctx: Context, parent: ViewGroup?): View

    abstract fun showItem(pos: Int, d: D?)

    open fun showItem(pos: Int, d: D?, payloads: MutableList<Any>){
        
    }

    open fun onCreate(ctx: Context, parent: ViewGroup?) {

    }

    fun isMatch(clazz: Class<*>) = clazz.hashCode() == dataHashCode
}