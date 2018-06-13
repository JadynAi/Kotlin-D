package com.motong.cm.kotlintest.rdsll

import android.support.annotation.LayoutRes

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

    fun showItem(d: D, pos: Int)
}

class AcrobatDSL<D> {

    @LayoutRes
    private var resId: Int = -1

    private var dataBind: (d: D, pos: Int) -> Unit = { _: D, _: Int -> Unit }

    fun resId(@LayoutRes resId: Int) {
        this.resId = resId
    }

    fun showItem(dataBind: (d: D, pos: Int) -> Unit) {
        this.dataBind = dataBind
    }

    internal fun build(): AcrobatItem<D> {
        return object : AcrobatItem<D> {
            override fun getResId(): Int = resId

            override fun showItem(d: D, pos: Int) {
                dataBind(d, pos)
            }
        }
    }

}