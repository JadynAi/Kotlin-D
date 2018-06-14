package com.motong.cm.kotlintest.rdsll

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/13
 *@ChangeList:
 */
class AcrobatMgr<D> {

    internal val items: ArrayList<AcrobatItem<D>> by lazy {
        ArrayList<AcrobatItem<D>>()
    }

    internal val itemConfigSet: HashSet<AcrobatDSL<D>> by lazy {
        HashSet<AcrobatDSL<D>>()
    }

    internal val data by lazy {
        ArrayList<D?>()
    }

    fun item(create: () -> AcroLayoutItem<D>) {
        items.add(create())
    }

    fun itemDSL(create: AcroLayoutDSL<D>.() -> Unit) {
        val acroLayoutDSL = AcroLayoutDSL<D>()
        acroLayoutDSL.create()
        items.add(acroLayoutDSL.build())
    }

    fun itemConfig(config: AcrobatDSL<D>.() -> Unit) {
        val acrobatDSL = AcrobatDSL<D>()
        acrobatDSL.config()
        itemConfigSet.add(acrobatDSL)
    }

    fun newData(list: List<D?>): ArrayList<AcrobatItem<D>> {
        val newList = ArrayList<AcrobatItem<D>>()
        items.forEach {
            if (it is AcroLayoutItem<D>) {
                newList.add(it)
            }
        }
        list?.forEachIndexed { index, d ->
            if (itemConfigSet.isEmpty()) {
                throw RuntimeException("Item must config!")
            } else {
                if (itemConfigSet.size == 1) {
                    newList.add(itemConfigSet.first().clone().build())
                } else {
                    itemConfigSet.forEach {
                        val build = it.build()
                        if (build.isMeetData(d, index)) {
                            newList.add(build)
                        }
                    }
                }
            }
        }
        return newList
    }

    fun refreshData(list: List<D?>, newData: ArrayList<AcrobatItem<D>>) {
        data.clear()
        items.forEach {
            if (it is AcroLayoutItem<D>) {
                data.add(null)
            }
        }
        data.addAll(list)
        items.clear()
        items.addAll(newData)
    }
}