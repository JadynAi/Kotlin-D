package com.jadyn.ai.kotlind.utils

import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/7/17
 *@ChangeList:
 */
fun <T> List<T>.getSafe(index: Int): T {
    val i = if (index < 0) 0 else (if (index > lastIndex) lastIndex else index)
    return get(i)
}

fun <T> List<T>.getSafeNull(index: Int): T? {
    if (index < 0 || index > lastIndex) {
        return null
    }
    return get(index)
}

fun <T> List<T>.indexOfSafe(t: T): Int {
    return if (indexOf(t) < 0) 0 else indexOf(t)
}

fun <T> List<T>?.isValid(): Boolean {
    return this != null && isNotEmpty()
}

fun <T> List<T>?.findIndex(predicate: (T) -> Boolean): Int {
    this?.apply {
        find(predicate)?.let {
            return this.indexOfSafe(it)
        }
    }
    return 0
}

fun String?.isValid(): Boolean {
    return this != null && isNotBlank()
}

fun String.toGBK(): ByteArray {
    return toByteArray(Charset.forName("GBK"))
}

fun <T> ArrayList<T>.removeAtSafe(index: Int) {
    if (index < 0 || index > lastIndex) {
        return
    }
    removeAt(index)
}

val <D> ConcurrentLinkedDeque<D>.firstSafe: D?
    get() {
        return try {
            first
        } catch (e: Exception) {
            null
        }
    }

val <D> ConcurrentLinkedDeque<D>.popSafe: D?
    get() {
        return try {
            pop()
        } catch (e: Exception) {
            null
        }
    }

val <D> ConcurrentLinkedDeque<D>.lastSafe: D?
    get() {
        return try {
            last
        } catch (e: Exception) {
            null
        }
    }