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

    internal val itemConfigs: HashSet<AcrobatItem<D>> by lazy {
        HashSet<AcrobatItem<D>>()
    }

    fun item(create: () -> AcrobatItem<D>) {
        items.add(create())
    }

    fun itemConfig(config: AcrobatDSL<D>.() -> Unit) {
        val acrobatDSL = AcrobatDSL<D>()
        acrobatDSL.config()
        itemConfigs.add(acrobatDSL.build())
    }

    fun setData(list: List<D>) {

    }
}