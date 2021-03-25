package com.jadyn.kotlinp.leetcode.sort

import java.util.*
import kotlin.math.min

/**
 *JadynAi since 3/25/21
 */
fun main() {
    print("sort ${Arrays.toString(insertSort(arrayOf(3, 1, 9, 10, 7, 5, 4)))}")
}

// 选择排序，每一轮选出最小的值
fun chooseSort(array: Array<Int>): Array<Int> {
    for (i in 0 until array.lastIndex) {
        var minPos = i
        for (j in i + 1 until array.size) {
            minPos = j.takeIf { array[j] < array[minPos] } ?: minPos
        }
        if (minPos != i) {
            val s = array[minPos]
            array[minPos] = array[i]
            array[i] = s
        }
    }
    return array
}

// 冒泡排序，让大的值浮上去
fun bubbleSort(array: Array<Int>): Array<Int> {
    for (i in 0 until array.lastIndex) {
        for (j in i + 1 until array.size) {
            if (array[i] > array[j]) {
                val jv = array[j]
                array[j] = array[i]
                array[i] = jv
            }
        }
    }
    return array
}

// 插入排序
fun insertSort(array: Array<Int>): Array<Int> {
    for (i in 1..array.lastIndex) {
        val i1 = array[i]
        l@ for (j in i - 1 downTo 0) {
            if (i1 > array[j]) {
                array[j + 1] = i1
                break@l
            } else {
                val jv = array[j]
                array[j + 1] = jv
                array[j] = i1
            }
        }
        println(Arrays.toString(array))
    }
    return array
}