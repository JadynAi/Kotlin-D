package com.jadyn.kotlinp.dynamic

import kotlin.math.max

/**
 *JadynAi since 4/20/21
 */
fun main() {
//    println("fib result ${maxSubArray(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4))}")
    println("counts ${climbStairs(4)}")
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

/**
 * 最大子序和
 * */
fun maxSubArray(nums: IntArray): Int {
    var sum = 0
    var maxAnswer = nums[0]
    for (num in nums) {
        sum = max(sum + num, num)
        maxAnswer = max(sum, maxAnswer)
    }
    return maxAnswer
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

/**
 * 爬n楼梯，每次只能爬1或者2，共有多少种解法
 * */
fun climbStairs(n: Int): Int {
    if (n <= 0) {
        return 0
    }
    val countsArray = arrayOfNulls<Int>(n)
    countsArray[0] = 1
    countsArray[1] = 2
    for (i in 2 until n) {
        countsArray[i] = countsArray[i - 1]!! + countsArray[i - 2]!!
    }
    return countsArray[n - 1]!!
}