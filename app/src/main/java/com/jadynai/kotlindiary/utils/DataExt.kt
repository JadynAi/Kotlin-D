package com.jadynai.kotlindiary.utils

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