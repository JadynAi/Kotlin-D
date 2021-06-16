package com.jadyn.kotlinp.thread

import com.jadyn.kotlinp.coroutine.printWithThreadName
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.withLock

/**
 *JadynAi since 6/12/21
 */
fun main() {
    testRWL()
}

fun testRWL() {
    val readWriteCache = ReadWriteCache()
    Thread {
        for (i in 0..100) {
            Thread.sleep(3000)
            readWriteCache.set(i, i * 8 + 10)
        }
    }.start()
    Thread {
        for (i in 0..100) {
            Thread.sleep(4000)
            printWithThreadName("get i $i ${readWriteCache.get(i)}")
        }
    }.start()
}

class ReadWriteCache {
    private val map = TreeMap<Int, Int>()
    private val rwl = ReentrantReadWriteLock()
    private val w = rwl.writeLock()
    private val r = rwl.readLock()

    fun get(i: Int): Int? {
        return r.withLock {
            map[i]
        }
    }

    fun set(i: Int, v: Int) {
        w.withLock {
            map[i] = v
        }
    }
}