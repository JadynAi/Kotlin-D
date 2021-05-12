package com.jadyn.kotlinp.leetcode.array

/**
 *JadynAi since 5/12/21
 */
fun main() {
    println("test ${removeDuplicates(intArrayOf(0, 0, 1, 1, 1, 2, 2))}")
}

/**
 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
 * 返回删除后数组的新长度.
 * 
 * 双指针：快慢指针
 * 关键点：实时修改数组的值
 * 快指针对比的条件是什么？
 * 
 * */
fun removeDuplicates(nums: IntArray): Int {
    if (nums.size <= 1) {
        return nums.size
    }
    var fast = 1
    var slow = 1
    while (fast < nums.size) {
        if (nums[fast] != nums[fast - 1]) {
            nums[slow] = nums[fast]
            slow++
        }
        fast++
    }
    return slow
}
