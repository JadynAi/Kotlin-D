package com.jadyn.kotlinp.leetcode.sort

import java.util.*

/**
 *JadynAi since 3/25/21
 */
fun main() {
//    print("sort ${Arrays.toString(fastSort(arrayOf(3, 1, 9, 10, 7, 5, 4)))}")
    val array = arrayOf(6, 10, 7, 9, 8, 11)
    insertSort(array)
    print("sort ${Arrays.toString(array)}")
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

// 冒泡排序，让大的值浮上去，递减循环
fun bubbleSort(array: Array<Int>): Array<Int> {
    for (i in 1 until array.size) {
        for (j in 0..array.lastIndex - i) {
            if (array[j] > array[j + 1]) {
                val jv = array[j]
                array[j] = array[j + 1]
                array[j + 1] = jv
            }
        }
        println("cur i $i arrays is ${Arrays.toString(array)}")
    }
    return array
}

// 插入排序，插入排序的核心思想就是把当前index之前的序列看作有序序列，从而和当前index
// 的值比较，小的就交换到前面去
fun insertSort(array: Array<Int>): Array<Int> {
    for (i in 1..array.lastIndex) {
        val i1 = array[i]
        l@ for (j in i - 1 downTo 0) {
            if (i1 > array[j]) {
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

// 快速排序
fun fastSort(array: Array<Int>, start: Int, end: Int) {
    if (end <= start) {
        return
    }
    val base = array[start]
    // 为什么双指针的left不能从start+1开始，因为这样就是默认右侧比start小了，当
    // 右侧的所有值比start大的话，left就不应该自增
    var left = start
    var right = end - 1
    while (left < right) {
        while (array[right] > base && left < right) {
            right--
        }
        while (array[left] <= base && left < right) {
            left++
        }
        if (left < right) {
            val v = array[left]
            array[left] = array[right]
            array[right] = v
        }
    }
    array[start] = array[left]
    array[left] = base
    println("${Arrays.toString(array)} left is $left right $right")
    fastSort(array, start, left)
    fastSort(array, left + 1, end)
}