package com.jadyn.ai.kotlin_d.utils

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

fun <T> List<T>.indexOfSafe(t: T): Int {
    return if (indexOf(t) < 0) 0 else indexOf(t)
}

fun <T> List<T>?.isValid(): Boolean {
    return this != null && isNotEmpty()
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