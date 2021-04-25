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
    var pre = 0
    var cur = 1
    for (i in 2..n) {
        val sum = pre + cur
        pre = cur
        cur = sum
    }
    return cur
}

fun coinChange(coins: IntArray, amount: Int): Int {
    if (amount == 0) return 0
    if (amount < 0) return -1
    coins.forEach {
        val subProblem = coinChange(coins, amount - it)
        if (subProblem == -1) {
            return@forEach
        }
    }
    return -1
}