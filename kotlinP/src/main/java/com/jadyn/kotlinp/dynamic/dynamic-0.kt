package com.jadyn.kotlinp.dynamic

/**
 *JadynAi since 4/20/21
 */
fun main() {
    println("fib result ${fib(4)}")
}

/**
 * 斐波那契数列
 * */
fun fib(n: Int): Int {
    if (n < 2) return n
    val array = arrayOfNulls<Int>(n + 1)
    array[0] = 0
    array[1] = 1
    for (i in 2..n) {
        array[i] = array[i - 1]!! + array[i - 2]!!
    }
    return array[n]!!
}