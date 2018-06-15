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
        items.takeWhile { it is AcroLayoutItem<D> }.forEach {
            newList.add(it)
        }
        list?.forEachIndexed { index, d ->
            newList.add(getMatchedItem(index, d))
        }
        return newList
    }

    private fun getMatchedItem(index: Int, d: D?): AcrobatItem<D> {
        if (itemConfigSet.isEmpty()) {
            throw RuntimeException("Item must config!")
        } else {
            if (itemConfigSet.size == 1) {
                return itemConfigSet.first().clone().build()
            } else {
                itemConfigSet.forEach {
                    val acrobatItem = it.build()
                    if (acrobatItem.isMeetData(d, index)) {
                        return acrobatItem
                    }
                }
                throw RuntimeException("No match found item!")
            }
        }
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