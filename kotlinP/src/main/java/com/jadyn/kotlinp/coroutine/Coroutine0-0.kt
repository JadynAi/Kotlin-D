package com.jadyn.kotlinp.coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

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
        launch {
            val async = async {
                printWithThreadNameAndTime("launch start")
                delay(1000)
                printWithThreadName("launch finish")
            }
            val async1 = async() {
                printWithThreadNameAndTime("global launch start")
                delay(500)
                printWithThreadName("global launch finish")
            }
            delay(3000)
//            async.await()
//            async1.await()
        }
    }
}
