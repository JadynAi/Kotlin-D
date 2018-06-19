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
interface AcrobatItem<D> {

    @LayoutRes
    fun getResId(): Int

    fun showItem(d: D?, pos: Int, view: View)

    fun isMeetData(d: D?, pos: Int): Boolean

    fun onItemAttachWindow(pos: Int, itemView: View?)

    fun showItem(d: D?, pos: Int, view: View, payloads: MutableList<Any>) {

    }
}

open class AcrobatDSL<D> : Cloneable {

    @LayoutRes
    protected var resId: Int = -1

    private var dataBind: (d: D?, pos: Int, view: View) -> Unit = { _: D?, _: Int, _: View -> Unit }

    private var dataPayload: (d: D?, pos: Int, view: View, p: MutableList<Any>) -> Unit = { _: D?, _: Int, _: View, _: MutableList<Any> -> Unit }

    private var dataMeet: (d: D?, pos: Int) -> Boolean = { _: D?, _: Int -> false }

    protected var viewAttach: (pos: Int, view: View?) -> Unit = { _: Int, _: View? -> Unit }

    open fun resId(@LayoutRes resId: Int) {
        this.resId = resId
    }

    fun showItem(dataBind: (d: D?, pos: Int, view: View) -> Unit) {
        this.dataBind = dataBind
    }

    fun isMeetData(dataMeet: (d: D?, pos: Int) -> Boolean) {
        this.dataMeet = dataMeet
    }

    open fun onViewAttach(viewAttach: (pos: Int, view: View?) -> Unit) {
        this.viewAttach = viewAttach
    }

    fun showItemPayload(dataPayload: (d: D?, pos: Int, view: View, payloads: MutableList<Any>) -> Unit) {
        this.dataPayload = dataPayload
    }

    public override fun clone(): AcrobatDSL<D> {
        val acrobatDSL = AcrobatDSL<D>()
        acrobatDSL.resId = this.resId
        acrobatDSL.dataBind = this.dataBind
        acrobatDSL.dataMeet = this.dataMeet
        acrobatDSL.dataPayload = this.dataPayload
        return acrobatDSL
    }

    override fun equals(other: Any?): Boolean {
        if (other is AcrobatDSL<*>) {
            other.dataPayload.equals(this@AcrobatDSL.dataPayload)
            return true
        } else {
            return false
        }
    }

    internal open fun build(): AcrobatItem<D> {
        return object : AcrobatItem<D> {

            override fun onItemAttachWindow(pos: Int, itemView: View?) {
                viewAttach(pos, itemView)
            }

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