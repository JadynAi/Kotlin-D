package com.jadyn.kotlinp.thread

import com.jadyn.kotlinp.coroutine.printWithThreadName
import java.util.concurrent.atomic.AtomicInteger

/**
 *JadynAi since 5/19/21
 */
fun main() {
    val b = ai.compareAndSet(2, 3)
    println("atomic integer ${b} ${ai.get()}")
    test()
}

private val ai = AtomicInteger(2)

private fun test() {
    val thread = Thread {
        for (i in 0..9) {
            Thread.sleep(500)
            val d = ai.compareAndSet(i + 1, i + 2)
            printWithThreadName("cas is $d, update is ${i + 2}, ai ${ai.get()}")
        }
    }
    val thread1 = Thread {
        for (i in 0..9) {
            Thread.sleep(800)
            val d = ai.compareAndSet(i + 2, i + 5)
            printWithThreadName("cas is $d, update is ${i + 2}, ai ${ai.get()}")
        }
    }
    thread.start()
    thread1.start()
    val thread2 = Thread {
        thread1.join()
        thread.join()
        printWithThreadName("ai ${ai.get()}")
    }.start()
}