package com.jadyn.ai.acrobat.recyclerview

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/13
 *@ChangeList:
 */
class AcrobatMgr<D>(private val a: AcrobatAdapter<D>) {

    internal val items: ArrayList<AcrobatItem<D>> by lazy {
        ArrayList<AcrobatItem<D>>()
    }

    internal val data by lazy {
        ArrayList<D>()
    }

    fun item(create: () -> AcrobatItem<D>) {
        val item = create()
        item.adapter = a
        items.add(item)
    }

    fun itemDSL(create: AcrobatDSL<D>.() -> Unit) {
        val acrobatDSL = AcrobatDSL<D>()
        acrobatDSL.adapter = a
        acrobatDSL.create()
        items.add(acrobatDSL.build())
    }

    fun setData(list: List<D>) {
        data.clear()
        data.addAll(list)
    }

    fun getItemConfig(position: Int, d: List<D> = data): Int {
        if (items.isEmpty()) {
            throw RuntimeException("item must config")
        }
        if (items.size == 1) {
            return 0
        }
        items.forEachIndexed { index, acrobatItem ->
            if (acrobatItem.isMeetData(d[position], position)) {
                return index
            }
        }
        throw RuntimeException("can't find matched item")
    }
}