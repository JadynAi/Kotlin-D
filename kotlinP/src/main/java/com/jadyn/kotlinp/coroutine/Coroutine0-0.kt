package com.jadyn.kotlinp.coroutine

import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

/**
 *JadynAi since 2020/9/19
 */
fun printWithThreadName(content: String) {
    println("$content | thread :${Thread.currentThread().name}")
}

fun printWithThreadNameAndTime(content: String) {
    println("$content | thread :${Thread.currentThread().name} | ${System.currentTimeMillis()}")
}

// 模拟android主线程
val mainExecutors by lazy {
    Executors.newSingleThreadExecutor {
        val t = Thread(Thread.currentThread().threadGroup, it,
                "Main",
                0)
        if (t.isDaemon) t.isDaemon = false
        if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
        t
    }
}

val singleExecutors by lazy {
    Executors.newSingleThreadExecutor {
        val t = Thread(Thread.currentThread().threadGroup, it,
                "single",
                0)
        if (t.isDaemon) t.isDaemon = false
        if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
        t
    }
}

fun main() {
    BaseMainTest().run()
}

open class BaseMainTest : CoroutineScope {

    // 这种复现方法，类中有一个成员变量 private final CoroutineContext coroutineContext
    // 也就是说外部在调用的时候，永远都是同一个对象
    override val coroutineContext = SupervisorJob() + mainExecutors.asCoroutineDispatcher()

    // 2020/10/9-15:40 使用这种写法，外部的Scope使用cancel就无法正常工作，wh……at？？？这什么原理
    // 2020/10/9-15:43 这种复写方法，会导致外部调用的时候每次都会生成一个新的对象，从而无法确保是同一个context对象
    // private final CoroutineContext coroutineContext，也就是说外部再调用的时候永远都是会新生成一个新的scope
//    override val coroutineContext: CoroutineContext
//        get() = SupervisorJob() + mainExecutors.asCoroutineDispatcher()

    open fun run() {
//        launch {
//            printWithThreadNameAndTime("start")
//            val s = arrayListOf<Deferred<Double>>()
//            for (i in 0..5) {
//                val t = test()
//                s.add(t)
//            }
//            s.awaitAll()
//            printWithThreadNameAndTime("end")
//            test1()
//            printWithThreadName("test1 end")
//        }
        val d = arrayListOf(0, 0, 0, 1, 2, -1)
        val d1 = ArrayList(d)
        d.clear()
        d.addAll(arrayListOf(3, 3, 9, 10, 11))
        printWithThreadName("d1 ${d1} ddd $d")
    }

    private fun test() = async(Dispatchers.IO) {
        delay(3000)
        printWithThreadNameAndTime("test happen")
        Random(5).nextDouble()
    }

    private suspend fun test1() = withContext(Dispatchers.IO) {
        delay(5000)
    }
}
