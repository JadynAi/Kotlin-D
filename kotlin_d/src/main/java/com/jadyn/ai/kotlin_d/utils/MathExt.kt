package com.jadyn.ai.kotlin_d.utils

/**
 *@version:
 *@FileDescription: 数学相关计算类
 *@Author:jing
 *@Since:2019/2/22
 *@ChangeList:
 */


/*
* 选取两个值中，差值绝对值小的那个数字
* */
fun Long.minDifferenceValue(a: Long, b: Long): Long {
    if (a == b) {
        return Math.min(a, this)
    }
    val f_a = Math.abs(a - this)
    val f_b = Math.abs(b - this)
    if (f_a == f_b) {
        return Math.min(a, b)
    }
    return if (f_a < f_b) a else b
}