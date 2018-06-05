package com.motong.cm.kotlintest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/5
 *@ChangeList:
 */
class RAdapter<D> private constructor(ctx: Context) : RecyclerView.Adapter<ViewHolderK>() {

    var data: ArrayList<D> = ArrayList<D>()
        get() {
            var d = ArrayList<D>()
            field.forEach {
                d.add(it)
            }
            return d
        }
        set(value) {
            field.clear()
            field.addAll(value)
        }

    private val itemViewMgr = ItemViewMgr()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderK {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolderK, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addItemClass(clazz: Class<*>) = apply { 
        itemViewMgr.addItemClass(clazz)
    }

    companion object {

        fun <D> create(context: Context, init: RAdapter<D>.() -> Unit): RAdapter<D> {
            return RAdapter(context)
        }
    }

}

class ViewHolderK(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

}