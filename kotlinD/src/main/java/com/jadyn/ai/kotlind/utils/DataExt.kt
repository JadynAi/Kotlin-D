package com.jadyn.ai.kotlind.utils

import android.util.Range
import androidx.collection.ArrayMap
import java.nio.charset.Charset
import java.util.*

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
    val i = indexOf(t)
    return if (i < 0) 0 else i
}

fun <T> List<T>.indexOfNull(t: T): Int? {
    val i = indexOf(t)
    return if (i < 0) null else i
}

fun <T> List<T>?.isValid(): Boolean {
    return this != null && isNotEmpty()
}

fun <T> List<T>?.findIndex(predicate: (T) -> Boolean): Int? {
    this?.apply {
        find(predicate)?.let {
            return this.indexOfNull(it)
        }
    }
    return null
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

val <D> Deque<D>.firstSafe: D?
    get() {
        return try {
            first
        } catch (e: Exception) {
            null
        }
    }

val <D> Deque<D>.popSafe: D?
    get() {
        return try {
            pop()
        } catch (e: Exception) {
            null
        }
    }

val <D> Deque<D>.lastSafe: D?
    get() {
        return try {
            last
        } catch (e: Exception) {
            null
        }
    }

val <D> Queue<D>.firstSafe: D?
    get() {
        return try {
            peek()
        } catch (e: Exception) {
            null
        }
    }

val <D> Queue<D>.pollSafe: D?
    get() {
        return try {
            poll()
        } catch (e: Exception) {
            null
        }
    }

/**
 * 区间[)
 * */
fun <D> List<D>.subListSafe(range: Range<Int>): List<D> {
    val start = if (range.lower < 0) 0 else range.lower
    val end = if (range.upper > size) size else range.upper
    return subList(start, end)
}

/**
 * Androidx arrayMap
 * */
fun <K, V> weakMapOf(vararg pairs: Pair<K, V>): WeakHashMap<K, V> =
        WeakHashMap<K, V>(pairs.size).apply { putAll(pairs) }

/**
 * Androidx arrayMap
 * */
fun <K, V> arrayXMapOf(vararg pairs: Pair<K, V>): ArrayMap<K, V> =
        ArrayMap<K, V>(pairs.size).apply { putAll(pairs) }

inline fun <T, R> Iterable<T>.mapUntil(transform: (T) -> R, judge: (T) -> Boolean): List<R> {
    return mapToUntil(ArrayList(), transform, judge)
}

inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapToUntil(destination: C, transform: (T) -> R, judge: (T) -> Boolean): C {
    for (item in this) {
        if (judge.invoke(item)) {
            break
        }
        destination.add(transform(item))
    }
    return destination
}

/**
 * 将一个list分为 sub 份
 * */
//fun <T> partition(list: List<T>, sub: Int): List<List<T>> {
//    if (list.isEmpty() || list.size <= sub) {
//        return arrayListOf(list)
//    }
//    val s = list.size
//    return Lists.partition(list, s / sub + s % sub)
//}

fun parseInt(num: String?, def: Int = 0): Int {
    if (num.isNullOrBlank()) {
        return def
    }
    return try {
        num.toInt()
    } catch (e: Exception) {
        def
    }
}