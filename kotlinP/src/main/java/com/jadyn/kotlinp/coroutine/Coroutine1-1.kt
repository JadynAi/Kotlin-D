package com.jadyn.kotlinp.coroutine

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

/**
 *JadynAi since 2020/9/17
 */
fun main(args: Array<String>) {
    A().run()
}

class A : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + mainExecutors.asCoroutineDispatcher()

    fun run() {
        launch {
            printWithThreadName("thread")
            startIO()
            printWithThreadName("get IO result ${Thread.currentThread().name} ")
        }
    }

    suspend fun startIO() {
        coroutineScope {
            launch(Dispatchers.IO) {
                printWithThreadName("IO start")
                delay(1000)
                printWithThreadName("IO finish")
            }
        }
    }

    private fun printWithThreadName(content: String) {
        println("$content | thread :${Thread.currentThread().name}")
    }

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