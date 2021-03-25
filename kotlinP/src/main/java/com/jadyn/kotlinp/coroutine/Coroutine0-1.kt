package com.jadyn.kotlinp.coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

/**
 *JadynAi since 2020/9/17
 */
fun main(args: Array<String>) {
//    A().run()
    A().runCoroutineScope()
}

class A : CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + mainExecutors.asCoroutineDispatcher()

    fun run() {
        launch {
            printWithThreadName("thread")
            startIO()
            printWithThreadName("get IO result")
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

    fun runCoroutineScope() {
        launch {
            val runTwoAsync = runTwoAsync()
            printWithThreadNameAndTime("run fi $runTwoAsync")
        }
    }

    suspend fun runTwoTask() {
        coroutineScope {
            launch {
                printWithThreadNameAndTime("launch start")
                delay(1000)
                printWithThreadNameAndTime("launch finish")
            }
            async {
                printWithThreadNameAndTime("async start")
                delay(500)
                printWithThreadNameAndTime("async finish")
            }
        }
        // 确保执行到这一步的时候coroutineScope内的任务都执行完毕
        printWithThreadNameAndTime("two task finish")
    }

    suspend fun runTwoAsync(): Int {
        val async = async {
            delay(1000)
            printWithThreadNameAndTime("async 1")
            2
        }
        val async1 = async {
            delay(500)
            printWithThreadNameAndTime("async 2")
            1
        }
        // 1 launch先执行，说明await有阻塞作用
        printWithThreadNameAndTime("1 launch ")
        return async.await() + async1.await()
    }
}
