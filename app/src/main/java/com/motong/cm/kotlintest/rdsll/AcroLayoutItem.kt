package com.motong.cm.kotlintest.rdsll

import android.support.annotation.LayoutRes
import android.view.View

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/14
 *@ChangeList:
 */
abstract class AcroLayoutItem<D> : AcrobatItem<D> {

    abstract fun showItem(pos: Int, view: View)

    open fun showItem(pos: Int, view: View, payloads: MutableList<Any>) {

    }

    override fun isMeetData(d: D?, pos: Int): Boolean = false

    override final fun showItem(d: D?, pos: Int, view: View) {
        showItem(pos, view)
    }

    override final fun showItem(d: D?, pos: Int, view: View, payloads: MutableList<Any>) {
        showItem(pos, view, payloads)
    }
}

class AcroLayoutDSL<D> : AcrobatDSL<D>() {

    private var dataBind: (pos: Int, view: View) -> Unit = { _: Int, _: View -> Unit }

    override fun resId(@LayoutRes resId: Int) {
        this.resId = resId
    }

    fun showItem(dataBind: (pos: Int, view: View) -> Unit) {
        this.dataBind = dataBind
    }

    override fun build(): AcroLayoutItem<D> {
        return object : AcroLayoutItem<D>() {
            override fun showItem(pos: Int, view: View) {
                dataBind(pos, view)
            }

            override fun getResId(): Int = resId
        }
    }
}