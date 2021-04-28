package com.jadyn.kotlinp.dynamic

import kotlin.math.max

/**
 *JadynAi since 4/20/21
 */
fun main() {
//    println("fib result ${maxSubArray(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4))}")
//    println("counts ${climbStairs(4)}")
//    println("max profit ${maxProfit1(intArrayOf(3, 3, 6, 7, 1, 6, 5, 4, 10))}")
    println("机器人路径 ${uniquePaths1(3, 7)}")
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

/**
 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。
 * 设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0
 * */
fun maxProfit(prices: IntArray): Int {
    if (prices.size == 0) return 0
    var max = 0
    var min = prices[0]
    for (i in 0..prices.lastIndex) {
        if (prices[i] <= min) {
            min = prices[i]
        } else if (prices[i] - min > max) {
            max = prices[i] - min
        }
    }
    return if (max < 0) 0 else max
}

fun maxProfit1(prices: IntArray): Int {
    if (prices.size == 0) return 0
    var max = 0
    // 4/26/21-10:05 PM 前一天的利润
    var pre = 0
    for (i in 1..prices.lastIndex) {
        val diff = prices[i] - prices[i - 1]
        // 4/26/21-10:04 PM 状态转移方程，i天的利润就是前一天的利润，加上i天的i-1的利润差
        pre = Math.max(0, pre + diff)
        max = Math.max(max, pre)
    }
    return max
}

/**
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 * 问总共有多少条不同的路径？
 * */
fun uniquePaths(m: Int, n: Int): Int {
    val dp = Array<IntArray>(m) {
        if (it == 0) {
            IntArray(n) { 1 }
        } else {
            IntArray(n) { na -> if (na == 0) 1 else 0 }
        }
    }
//    dp.forEachIndexed { index, ints ->
//        ints.forEachIndexed { j, na ->
//            // 4/28/21-10:13 PM 这么写的判断条件，为什么不直接从1开始遍历呢？
//            // 固化、固化！
//            if (index != 0 && j != 0) {
//                dp[index][j] = dp[index - 1][j] + dp[index][j - 1]
//            }
//        }
//    }
    for (i in 1 until m) {
        for (j in 1 until n) {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
        }
    }
    return dp[m - 1][n - 1]
}

/**
 * 优化机器人的控件复杂度为O(n)
 * 相当于把短数组按照max次数过了一遍
 * d[j] 就相当于是cur dp[j-1]相当于是pre
 * */
fun uniquePaths1(m: Int, n: Int): Int {
    val min = Math.min(m, n)
    val max = Math.max(m, n)
    val dp = IntArray(min) { 1 }
    for (i in 1 until max) {
        for (j in 1 until min) {
            println("i $i j $j  dp ${dp[j]}")
            dp[j] += dp[j - 1]
        }
    }
    return dp[min - 1]
}