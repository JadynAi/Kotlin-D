package com.jadyn.kotlinp.coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

/**
 *JadynAi since 2020/9/19
 */
fun printWithThreadName(content: String) {
    println("$content | thread :${Thread.currentThread().name}")
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

fun main() {
    BaseMainTest().run()
}

open class BaseMainTest : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = mainExecutors.asCoroutineDispatcher()

    open fun run() {
        launch { 
            delay(1000)
            printWithThreadName("launch finish")
        }
        launch { 
            delay(500)
            printWithThreadName("global launch finish")
        }
        printWithThreadName("run finish")
    }

}
