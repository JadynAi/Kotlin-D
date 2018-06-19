package com.motong.cm.kotlintest.rdsll

import android.support.annotation.LayoutRes
import android.view.View

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/13
 *@ChangeList:
 */
interface AcrobatItem<D> : Cloneable {

    @LayoutRes
    fun getResId(): Int

    fun showItem(d: D?, pos: Int, view: View)

    fun isMeetData(d: D?, pos: Int): Boolean = true

    fun showItem(d: D?, pos: Int, view: View, payloads: MutableList<Any>) {

    }

    public override fun clone(): AcrobatItem<D> {
        return object : AcrobatItem<D> {
            override fun getResId(): Int = this@AcrobatItem.getResId()

            override fun showItem(d: D?, pos: Int, view: View) {
                this@AcrobatItem.showItem(d, pos, view)
            }

            override fun showItem(d: D?, pos: Int, view: View, payloads: MutableList<Any>) {
                super.showItem(d, pos, view, payloads)
                this@AcrobatItem.showItem(d, pos, view, payloads)
            }

            override fun isMeetData(d: D?, pos: Int): Boolean = this@AcrobatItem.isMeetData(d, pos)
        }
    }
}

open class AcrobatDSL<D> constructor(private inline var dataBind: (d: D?, pos: Int, view: View) -> Unit = { _: D?, _: Int, _: View -> Unit },

                                     private inline var dataPayload: (d: D?, pos: Int, view: View, p: MutableList<Any>) -> Unit = { _: D?, _: Int, _: View, _: MutableList<Any> -> Unit },

                                     private inline var dataMeet: (d: D?, pos: Int) -> Boolean = { _: D?, _: Int -> false }) {

    @LayoutRes
    protected var resId: Int = -1

    open fun resId(@LayoutRes resId: Int) {
        this.resId = resId
    }

    fun showItem(dataBind: (d: D?, pos: Int, view: View) -> Unit) {
        this.dataBind = dataBind
    }

    fun isMeetData(dataMeet: (d: D?, pos: Int) -> Boolean) {
        this.dataMeet = dataMeet
    }

    fun showItemPayload(dataPayload: (d: D?, pos: Int, view: View, payloads: MutableList<Any>) -> Unit) {
        this.dataPayload = dataPayload
    }

    internal open fun build(): AcrobatItem<D> {
        return object : AcrobatItem<D> {
            override fun isMeetData(d: D?, pos: Int): Boolean {
                return dataMeet(d, pos)
            }

            override fun getResId(): Int = resId

            override fun showItem(d: D?, pos: Int, view: View) {
                dataBind(d, pos, view)
            }

            override fun showItem(d: D?, pos: Int, view: View, payloads: MutableList<Any>) {
                dataPayload(d, pos, view, payloads)
            }
        }
    }

}