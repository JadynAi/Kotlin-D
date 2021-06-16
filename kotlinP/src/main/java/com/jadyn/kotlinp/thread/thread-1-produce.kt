package com.jadyn.kotlinp.thread

import com.jadyn.kotlinp.coroutine.printWithThreadName
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

/**
 *JadynAi since 5/4/21
 *
 * 生产者消费者队列
 */
fun main() {
    Thread {
        for (i in 0..9) {
            produce1()
            Thread.sleep(500)
        }
    }.start()
    Thread {
        for (i in 0..9) {
            consume1()
            Thread.sleep(1000)
        }
    }.start()
}

val MAX_SIZE = 10

val list = LinkedList<String>()

val lock = ReentrantLock()
val full = lock.newCondition()
val empty = lock.newCondition()

fun produce1() {
    lock.lock()
    while (list.size >= MAX_SIZE) {
        try {
            full.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    list.add("1")
    printWithThreadName("this produce is ${list.size}")
    empty.signalAll()
    lock.unlock()
}

fun consume1() {
    lock.lock()
    while (list.isEmpty()) {
        try {
            empty.await()
        } catch (e: Exception) {
        }
    }
    list.remove()
    printWithThreadName("consume is ${list.size}")
    full.signalAll()
    lock.unlock()
}