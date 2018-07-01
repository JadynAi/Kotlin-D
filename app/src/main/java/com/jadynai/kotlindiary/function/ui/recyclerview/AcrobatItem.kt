package com.jadynai.kotlindiary.function.ui.recyclerview

import android.support.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/13
 *@ChangeList:
 */
abstract class AcrobatItem<in D> internal constructor(var click: ((Int) -> Unit)? = null){

    @LayoutRes
    abstract fun getResId(): Int

    open fun onViewCreate(parent: ViewGroup, view: View) {
    }

    abstract fun showItem(d: D, pos: Int, view: View)

    open fun isMeetData(d: D, pos: Int): Boolean = true

    open fun showItem(d: D, pos: Int, view: View, payloads: MutableList<Any>) {

    }
}

class AcrobatDSL<D> constructor(private inline var create: (parent: ViewGroup, view: View) -> Unit = { _, _ -> Unit },

                                private inline var dataBind: (d: D, pos: Int, view: View) -> Unit = { _: D, _: Int, _: View -> Unit },

                                private inline var dataPayload: (d: D, pos: Int, view: View, p: MutableList<Any>) -> Unit = { _: D, _: Int, _: View, _: MutableList<Any> -> Unit },

                                private inline var dataMeet: (d: D, pos: Int) -> Boolean = { _: D, _: Int -> false },
                                
                                private var click: ((Int) -> Unit)? = null) {

    @LayoutRes
    private var resId: Int = -1

    fun resId(@LayoutRes resId: Int) {
        this.resId = resId
    }

    fun onViewCreate(create: (parent: ViewGroup, view: View) -> Unit) {
        this.create = create
    }

    fun showItem(dataBind: (d: D, pos: Int, view: View) -> Unit) {
        this.dataBind = dataBind
    }

    fun isMeetData(dataMeet: (d: D, pos: Int) -> Boolean) {
        this.dataMeet = dataMeet
    }

    fun showItemPayload(dataPayload: (d: D, pos: Int, view: View, payloads: MutableList<Any>) -> Unit) {
        this.dataPayload = dataPayload
    }

    fun onClick(event: (Int) -> Unit) {
        this.click = event
    }

    internal fun build(): AcrobatItem<D> {
        return object : AcrobatItem<D>(this.click) {
            override fun isMeetData(d: D, pos: Int): Boolean {
                return dataMeet(d, pos)
            }

            override fun onViewCreate(parent: ViewGroup, view: View) {
                super.onViewCreate(parent, view)
                create(parent, view)
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
    }

}