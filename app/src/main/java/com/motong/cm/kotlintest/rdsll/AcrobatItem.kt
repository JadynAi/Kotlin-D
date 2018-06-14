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
}

open class AcrobatDSL<D> : Cloneable {

    @LayoutRes
    protected var resId: Int = -1

    private var dataBind: (d: D?, pos: Int, view: View) -> Unit = { _: D?, _: Int, _: View -> Unit }

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

    public override fun clone(): AcrobatDSL<D> {
        val acrobatDSL = AcrobatDSL<D>()
        acrobatDSL.resId = this.resId
        acrobatDSL.dataBind = this.dataBind
        acrobatDSL.dataMeet = this.dataMeet
        return acrobatDSL
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
        }
    }

}