package com.jadyn.ai.acrobat.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/13
 *@ChangeList:
 */

abstract class AbsAcrobatItem<D, V>(var click: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
                                    var doubleTap: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
                                    var longPress: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null) {

    abstract fun getView(parent: ViewGroup): View

    open fun onViewCreate(parent: ViewGroup, v: V,
                          viewHolder: AcrobatAdapter.AcroViewHolder<D>) {
    }

    abstract fun showItem(d: D, pos: Int, v: V)

    abstract fun showItem(d: D, pos: Int, v: V, payloads: MutableList<Any>)

    abstract fun isMeetData(d: D, pos: Int): Boolean


    fun hasEvent() = click != null || doubleTap != null || longPress != null
}

abstract class AcrobatItem<D>(click: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
                              doubleTap: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
                              longPress: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null)
    : AbsAcrobatItem<D, View>(click, doubleTap, longPress)

abstract class AbsAcrobatDSLBuilder<D, V>(
        protected var create: (parent: ViewGroup, view: V, viewHolder: AcrobatAdapter.AcroViewHolder<D>) -> Unit = { _, _, _ -> Unit },
        protected var dataBind: (d: D, pos: Int, view: V) -> Unit = { _: D, _: Int, _: V -> Unit },
        protected var dataPayload: (d: D, pos: Int, view: V, p: MutableList<Any>) -> Unit = { _: D, _: Int, _: V, _: MutableList<Any> -> Unit },
        protected var dataMeet: (d: D, pos: Int) -> Boolean = { _: D, _: Int -> false },
        protected var click: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
        protected var doubleTap: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
        protected var longP: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
        protected var getView: ((parent: ViewGroup) -> View)? = null) {

    fun resId(@LayoutRes resId: Int) {
        getView = { parent: ViewGroup ->
            LayoutInflater.from(parent.context).inflate(resId, parent, false)
        }
    }

    open fun getView(vg: (parent: ViewGroup) -> View) {
        getView = vg
    }

    fun onViewCreate(create: (parent: ViewGroup, view: V,
                              viewHolder: AcrobatAdapter.AcroViewHolder<D>) -> Unit) {
        this.create = create
    }

    fun showItem(dataBind: (d: D, pos: Int, view: V) -> Unit) {
        this.dataBind = dataBind
    }

    fun isMeetData(dataMeet: (d: D, pos: Int) -> Boolean) {
        this.dataMeet = dataMeet
    }

    fun showItemPayload(
            dataPayload: (d: D, pos: Int, view: V, payloads: MutableList<Any>) -> Unit) {
        this.dataPayload = dataPayload
    }

    fun onClick(event: (d: D, vh: RecyclerView.ViewHolder) -> Unit) {
        this.click = event
    }

    fun onDoubleTap(dT: (d: D, vh: RecyclerView.ViewHolder) -> Unit) {
        this.doubleTap = dT
    }

    fun longPress(lp: (d: D, vh: RecyclerView.ViewHolder) -> Unit) {
        this.longP = lp
    }

    abstract fun build(): AbsAcrobatItem<D, V>
}

class AcrobatDSLBuilder<D> : AbsAcrobatDSLBuilder<D, View>() {

    override fun build(): AcrobatItem<D> {
        return object : AcrobatItem<D>(click, doubleTap, longP) {
            override fun isMeetData(d: D, pos: Int): Boolean {
                return dataMeet(d, pos)
            }

            override fun getView(parent: ViewGroup): View {
                return getView?.invoke(parent) ?: throw Exception("must use getResId or getView Function!")
            }

            override fun onViewCreate(parent: ViewGroup, v: View,
                                      viewHolder: AcrobatAdapter.AcroViewHolder<D>) {
                super.onViewCreate(parent, v, viewHolder)
                create(parent, v, viewHolder)
            }

            override fun showItem(d: D, pos: Int, v: View) {
                dataBind(d, pos, v)
            }

            override fun showItem(d: D, pos: Int, v: View, payloads: MutableList<Any>) {
                dataPayload(d, pos, v, payloads)
            }
        }
    }
}