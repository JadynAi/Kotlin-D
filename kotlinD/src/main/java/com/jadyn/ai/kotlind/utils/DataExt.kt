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

fun <T> MutableList<T?>.setSafe(index: Int, t: T?): Boolean {
    if (isEmpty()) {
        return false
    }
    if (index in 0..lastIndex) {
        this[index] = t
        return true
    }
    return false
}

fun <T> MutableList<T>.setSafeNoNone(index: Int, t: T): Boolean {
    if (isEmpty()) {
        return false
    }
    if (index in 0..lastIndex) {
        this[index] = t
        return true
    }
    return false
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

fun <T> List<T>.indexFilter(predicate: (T) -> Boolean): List<Int> {
    val indexList = arrayListOf<Int>()
    forEachIndexed { index, t ->
        if (predicate.invoke(t)) {
            indexList.add(index)
        }
    }
    return indexList
}

fun <T> List<T>?.isValid(): Boolean {
    return !isNullOrEmpty()
}

inline fun <T, R : Comparable<R>> MutableList<T>.addBySorted(t: T, crossinline compare: (T) -> R?) {
    add(t)
    sortedBy(compare)
}

inline fun <T, R : Comparable<R>> MutableList<T>.addAllBySorted(t: List<T>, crossinline compare: (T) -> R?) {
    addAll(t)
    sortedBy(compare)
}

inline fun <T, R : Comparable<R>> MutableList<T>.addBySortedDescending(t: T, crossinline compare: (T) -> R?) {
    add(t)
    sortedByDescending(compare)
}

fun <T> Array<T>?.isValid(): Boolean {
    return !isNullOrEmpty()
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
fun <D> List<D>.subListSafe(start: Int, end: Int): List<D> {
    if (isEmpty()) {
        return this
    }
    val s = if (start < 0) 0 else start
    val e = if (end > size) size else end
    return subList(s, e)
}


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
fun <T> partition(list: List<T>, sub: Int): List<List<T>> {
    if (sub <= 1) {
        return arrayListOf(list)
    }
    if (list.isEmpty() || list.size <= sub) {
        return arrayListOf(list)
    }
    val s = list.size
    return Lists.partition(list, s / sub + s % sub)
}

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

fun <T> MutableList<T>.addOnly(t: T) {
    if (contains(t)) {
        return
    }
    add(t)
}

fun <T> MutableList<T>.addOnlyNoNone(t: T?) {
    t?.apply { addOnly(this) }
}

fun <T> MutableList<T>.addNoNone(t: T?) {
    t?.apply { add(this) }
}

fun <T> MutableSet<T>.addNoNone(t: T?) {
    t?.apply { add(this) }
}

inline fun <reified T> Array<T>.deepCopy(transform: (T) -> T): Array<T> {
    return Array(this.size) {
        transform.invoke(this[it])
    }
}

inline fun <T> MutableList<T>.deepCopy(transform: (T) -> T): MutableList<T> {
    val list = this.javaClass.newInstance()
    forEach {
        list.add(transform.invoke(it))
    }
    return list
}

fun <K, V> Map<K, V>.toArray(): Array<Pair<K, V>> {
    return mapNotNull { Pair(it.key, it.value) }.toTypedArray()
}

inline fun <T, R : Comparable<R>> Iterable<T>.minByIndex(selector: (T) -> R): Pair<T, Int>? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null
    var minElem = iterator.next()
    if (!iterator.hasNext()) return minElem to 0
    var index = 0
    var r = index
    var minValue = selector(minElem)
    do {
        val e = iterator.next()
        val v = selector(e)
        index++
        if (minValue > v) {
            minElem = e
            minValue = v
            r = index
        }
    } while (iterator.hasNext())
    return minElem to r
}

inline fun <reified R> Iterable<*>.findIsInstance(): R? {
    return find { it is R } as? R
}