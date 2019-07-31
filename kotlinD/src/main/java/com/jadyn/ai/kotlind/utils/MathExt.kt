package com.jadyn.ai.kotlind.utils

import android.util.Size
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.*
import kotlin.math.max

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

fun createFloatBuffer(array: FloatArray): FloatBuffer {
    val buffer = ByteBuffer
            // 分配顶点坐标分量个数 * Float占的Byte位数
            .allocateDirect(array.size * 4)
            // 按照本地字节序排序
            .order(ByteOrder.nativeOrder())
            // Byte类型转Float类型
            .asFloatBuffer()

    // 将Dalvik的内存数据复制到Native内存中
    buffer.put(array).position(0)
    return buffer
}

fun Size.aspectRatio(): Float {
    return if (width > height)
        width.toFloat() / height.toFloat()
    else
        height.toFloat() / width.toFloat()
}

fun Size.swap(): Size {
    return Size(height, width)
}

fun Size.maxChoose(other: Size): Size {
    return Size(max(width, other.width), max(height, other.height))
}

fun <D> safeList() = Collections.synchronizedList(arrayListOf<D>())