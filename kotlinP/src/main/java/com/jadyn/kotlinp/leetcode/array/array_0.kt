package com.jadyn.kotlinp.leetcode.array

/**
 *JadynAi since 5/12/21
 */
fun main() {
//    println("test ${removeDuplicates(intArrayOf(0, 0, 1, 1, 1, 2, 2))}")
    println("test ${testFlatMap()}")
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

fun testFlatMap(): List<Int> {
    val o = arrayListOf(
        arrayListOf(1, 2, 3),
        arrayListOf(4, 6, 7),
        arrayListOf(12, 23, 9),
        arrayListOf(333, 33, 90, 5)
    )
    return o.flatMap { o1-> 
        val oo1 = arrayListOf<Int>()
        oo1.addAll(o1)
        oo1
    }
}
