package com.motong.cm.kotlintest.adapter

import android.support.v4.util.SparseArrayCompat

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/5
 *@ChangeList:
 */
class ItemViewMgr {

    private val itemClasses = SparseArrayCompat<Class<*>>()

    fun addItemClass(clazz: Class<*>) {
        itemClasses.put(itemClasses.size(), clazz)
    }
}