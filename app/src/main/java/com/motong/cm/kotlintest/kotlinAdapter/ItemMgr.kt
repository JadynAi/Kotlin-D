package com.laihua.framework.ui.adapter.kotlinAdapter

import android.support.v4.util.SparseArrayCompat
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/7
 *@ChangeList:
 */
internal class ItemMgr<D>{

    private val itemViewClasses: SparseArrayCompat<Class<out AbsItemViewK<D>>> by lazy {
        SparseArrayCompat<Class<out AbsItemViewK<D>>>()
    }

    fun putItemClass(clazz: Class<out AbsItemViewK<D>>) {
        val dataClass = getGenericsClass(clazz, AbsItemViewK::class.java)
        itemViewClasses.put(dataClass.hashCode(), clazz)
    }

    fun isSingleType(): Boolean = itemViewClasses.size() <= 1

    fun getItemType(hashCode: Int?): Int {
        hashCode?.apply {
            return itemViewClasses.indexOfKey(hashCode).apply {
                if (this < 0) {
                    throw RuntimeException("not find item type")
                }
            }
        }
        throw RuntimeException("not find item type")
    }

    fun createItemView(viewType: Int): AbsItemViewK<D> {
        val itemViewK = itemViewClasses.valueAt(viewType).newInstance()
        itemViewK.dataHashCode = itemViewClasses.keyAt(viewType)
        return itemViewK
    }
}

fun getGenericsClass(curClass: Class<*>, genericsClass: Class<*>): Class<*> {
    var superType: Type = curClass.genericSuperclass
    while (true) {
        if (superType is Class<*>) {
            superType = superType.genericSuperclass
            continue
        }
        if (superType is ParameterizedType) {
            var superParamType: ParameterizedType = superType
            if (!genericsClass.equals(superParamType.rawType)) {
                superType = superParamType.rawType.javaClass.genericSuperclass
                continue
            }
            val typeArray: Array<Type> = superParamType.actualTypeArguments
            val typeArgument = typeArray[0]
            if (typeArgument is Class<*>) {
                return typeArgument.javaClass
            } else if (typeArgument is ParameterizedType) {
                return typeArgument.rawType.javaClass
            }
        }
        throw IllegalArgumentException("curClass" + curClass + "nor find superclassParam")
    }
}