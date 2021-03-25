package com.jadyn.kotlinp.leetcode.thought

import org.w3c.dom.Node
import java.util.*
import kotlin.math.max

/**
 *JadynAi since 3/21/21
 *
 * 双指针
 */
fun main() {
//    print("find two sub ${findTwoNumSub(arrayOf(1, 2, 4, 6, 9), 14)}")
    print("judgeSquareSum ${judgeSquareSum(1000)}")
}

// 在有序数组中找出两个数，使它们的和为 target，返回index
fun findTwoNumSub(array: Array<Int>, target: Int): IntArray? {
    if (array.isEmpty()) return null
    var leftIndex = 0
    var rightIndex = array.lastIndex
    // 想一下这里能用这个判断条件吗？不可以，这里的判断条件就是假设一定可以找到
//    while (array[leftIndex] + array[rightIndex] != target) {
    while (leftIndex < rightIndex) {
        val left = array[leftIndex]
        val right = array[rightIndex]
        when {
            left + right > target -> {
                rightIndex--
            }
            left + right < target -> {
                leftIndex++
            }
            else -> {
                return intArrayOf(leftIndex, rightIndex)
            }
        }
    }
    return null
}

// 给定一个非负整数 c ，你要判断是否存在两个整数 a 和 b，使得 a2 + b2 = c 
fun judgeSquareSum(target: Int): Boolean {
    if (target <= 0) return false
    var left = 0
    // 双指针，关键是右侧指针的选取值，可以将target的sqrt值看作最大值
    var right = Math.sqrt(target.toDouble()).toInt()
    while (left <= right) {
        val squareSum = left * left + right * right
        when {
            squareSum == target -> return true
            squareSum > target -> {
                right--
            }
            else -> {
                left++
            }
        }
    }
    return false
}

// 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
//初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。你可以假设 nums1 的空间大小等于 m + n，这样它就有足够的空间保存来自 nums2 的元素
fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
    val nums1Copy = Arrays.copyOfRange(nums1, 0, m)
    var i = 0
    var j = 0
    var index = 0
    // 双指针比较，小的放进去nums1数组，然后数值小的指针继续走
    // 最后结束的条件，肯定就是有一方的指针走到了尽头
    while (i < m && j < n) {
        if (nums1Copy[i] < nums2[j]) {
            nums1[index] = nums1Copy[i]
            i++
        } else {
            nums1[index] = nums2[j]
            j++
        }
        index++
    }
    // 有一方的指针走到了尽头，那就ok，另外的那个数组剩下的值直接copy进去nums1数组就ok
    if (i == m) {
        System.arraycopy(nums2, j, nums1, index, n - j - 1)
    } else {
        System.arraycopy(nums1Copy, i, nums1, index, m - i - 1)
    }
}

