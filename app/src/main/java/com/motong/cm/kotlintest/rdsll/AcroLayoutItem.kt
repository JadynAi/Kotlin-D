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
interface AcroLayoutItem<D> : AcrobatItem<D> {

    fun showItem(pos: Int, view: View)

    override fun isMeetData(d: D?, pos: Int): Boolean = false

    override fun showItem(d: D?, pos: Int, view: View) {
        showItem(pos, view)
    }

    override open fun onItemAttachWindow(pos: Int, itemView: View?) {

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

    override fun onViewAttach(viewAttach: (pos: Int, view: View?) -> Unit) {
        this.viewAttach = viewAttach
    }

    override fun build(): AcroLayoutItem<D> {
        return object : AcroLayoutItem<D> {
            override fun showItem(pos: Int, view: View) {
                dataBind(pos, view)
            }
            override fun getResId(): Int = resId

            override fun onItemAttachWindow(pos: Int, itemView: View?) {
                viewAttach(pos, itemView)
            }
        }
    }
}