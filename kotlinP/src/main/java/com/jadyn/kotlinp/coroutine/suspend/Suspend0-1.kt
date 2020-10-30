package com.jadyn.kotlinp.coroutine.suspend

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadyn.kotlinp.coroutine.printWithThreadNameAndTime
import com.jadyn.kotlinp.coroutine.singleExecutors
import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

/**
 *JadynAi since 2020/10/29
 */
fun main() {
    SuspendTimeOutTest().run()
}

class SuspendTimeOutTest : BaseMainTest() {
    override fun run() {
        launch {
            repeat(5) {
                try {
                    withContext(singleExecutors.asCoroutineDispatcher()) {
                        val r = awaitSingleSuspend()
                        printWithThreadName("r is $r")
                        async {
                            printWithThreadNameAndTime("back to main")
                        }.await()
                    }
                } catch (e: Exception) {
                    printWithThreadName("with context failed ${e.message}")
                }
            }
        }
    }

    private suspend fun testTimeOut() {
        printWithThreadNameAndTime("time out start")
        withTimeoutOrNull(3600) {
            repeat(5) {
                delay(200)
                printWithThreadName("run once $it")
            }
            printWithThreadName("repeat run over")
        }
        printWithThreadNameAndTime("time out after end")
    }

    private suspend fun awaitSingleSuspend() = suspendCoroutineWithTimeout<String>(10) {
        it.invokeOnCancellation {
            printWithThreadName("invoke cancel")
        }
        printWithThreadName("single suspend start")
        runBlocking {
            printWithThreadName("single runBlocking start")
            delay(30)
            it.resume("single result")
        }
    }
}

suspend inline fun <T> suspendCoroutineWithTimeout(timeout: Long, crossinline block: (CancellableContinuation<T>) -> Unit): T {
    return withTimeout(timeout) { suspendCancellableCoroutine(block = block) }
}