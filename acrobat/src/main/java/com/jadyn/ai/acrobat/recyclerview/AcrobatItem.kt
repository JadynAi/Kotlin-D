package com.jadyn.ai.acrobat.recyclerview

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
abstract class AcrobatItem<D>(var click: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
                              var doubleTap: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,
                              var longPress: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null) {

    companion object {
        fun <D> create(dsl: AcrobatDSL<D>.() -> Unit): AcrobatItem<D> {
            val acrobatDSL = AcrobatDSL<D>()
            acrobatDSL.dsl()
            return acrobatDSL.build()
        }
    }

    internal lateinit var adapter: AcrobatAdapter<D>

    fun getTag(id: Int): Any? {
        return adapter.tagMap.get(id)
    }

    @LayoutRes
    abstract fun getResId(): Int

    open fun onViewCreate(parent: ViewGroup, view: View,
                          viewHolder: AcrobatAdapter.AcroViewHolder<D>) {
    }

    abstract fun showItem(d: D, pos: Int, view: View)

    open fun isMeetData(d: D, pos: Int): Boolean = true

    open fun showItem(d: D, pos: Int, view: View, payloads: MutableList<Any>) {

    }

    fun hasEvent() = click != null || doubleTap != null || longPress != null
}

class AcrobatDSL<D> constructor(
        private inline var create: (parent: ViewGroup, view: View, viewHolder: AcrobatAdapter.AcroViewHolder<D>) -> Unit = { _, _, _ -> Unit },

        private inline var dataBind: (d: D, pos: Int, view: View) -> Unit = { _: D, _: Int, _: View -> Unit },

        private inline var dataPayload: (d: D, pos: Int, view: View, p: MutableList<Any>) -> Unit = { _: D, _: Int, _: View, _: MutableList<Any> -> Unit },

        private inline var dataMeet: (d: D, pos: Int) -> Boolean = { _: D, _: Int -> false },

        private inline var click: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,

        private inline var doubleTap: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null,

        private inline var longP: ((d: D, vh: RecyclerView.ViewHolder) -> Unit)? = null) {

    internal lateinit var adapter: AcrobatAdapter<D>

    fun getTag(id: Int): Any? {
        return adapter.tagMap.get(id)
    }

    @LayoutRes
    private var resId: Int = -1

    fun resId(@LayoutRes resId: Int) {
        this.resId = resId
    }

    fun onViewCreate(create: (parent: ViewGroup, view: View,
                              viewHolder: AcrobatAdapter.AcroViewHolder<D>) -> Unit) {
        this.create = create
    }

    fun showItem(dataBind: (d: D, pos: Int, view: View) -> Unit) {
        this.dataBind = dataBind
    }

    fun isMeetData(dataMeet: (d: D, pos: Int) -> Boolean) {
        this.dataMeet = dataMeet
    }

    fun showItemPayload(
            dataPayload: (d: D, pos: Int, view: View, payloads: MutableList<Any>) -> Unit) {
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

    internal fun build(): AcrobatItem<D> {
        val value = object : AcrobatItem<D>(click, doubleTap, longP) {
            override fun isMeetData(d: D, pos: Int): Boolean {
                return dataMeet(d, pos)
            }

            override fun onViewCreate(parent: ViewGroup, view: View,
                                      viewHolder: AcrobatAdapter.AcroViewHolder<D>) {
                super.onViewCreate(parent, view, viewHolder)
                create(parent, view, viewHolder)
            }

            override fun getResId(): Int = resId

            override fun showItem(d: D, pos: Int, view: View) {
                dataBind(d, pos, view)
            }

            override fun showItem(d: D, pos: Int, view: View, payloads: MutableList<Any>) {
                super.showItem(d, pos, view, payloads)
                dataPayload(d, pos, view, payloads)
            }
        }
        value.adapter = this@AcrobatDSL.adapter
        return value
    }

}