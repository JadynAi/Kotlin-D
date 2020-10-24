package com.jadyn.kotlinp.coroutine.suspend

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadyn.kotlinp.coroutine.singleExecutors
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 *JadynAi since 2020/10/22
 */
fun main() {
    SuspendBaseTest().run()
}

class SuspendBaseTest : BaseMainTest() {

    override fun run() {
        launch {
            printWithThreadName("start")
            val channel = Channel<Int>()
            launch {
                channel.receiveAsFlow().collect {
                    printWithThreadName("顶层channel collect $it")
                }
            }
            repeat(1) {
                try {
                    withContext(Dispatchers.IO) {
                        runWithContext(it, channel)
                    }
                } catch (e: Exception) {
                    printWithThreadName("catch exception ${e.message}")
                }
            }
            printWithThreadName("最终最终执行close")
            channel.close()
            launch {
                printWithThreadName("other coroutine launch")
            }
        }
    }

    suspend fun runWithContext(num: Int, channel: Channel<Int>): Int {
        return async {
            printWithThreadName("collect start")
            val awaitTest = awaitTest(num)
            awaitTest.receiveAsFlow().collect {
                delay(200)
                channel.send(it)
                printWithThreadName("collect $it")
            }
            printWithThreadName("collect end")
            1024
        }.await()
    }
}

suspend fun awaitTest(num: Int) = suspendCancellableCoroutine<Channel<Int>> {
    printWithThreadName("await start")
    val channel = Channel<Int>(Channel.UNLIMITED)
    singleExecutors.execute {
        Thread.sleep(3000)
        printWithThreadName("await resume before")
        // test exception
        if (num == 1) {
            it.resumeWithException(Exception("suspend error"))
        } else {
            it.resume(channel)
        }
        repeat(3) {
            val offer = channel.offer(it)
//            printWithThreadName("offer $it $offer")
            Thread.sleep(20)
        }
        Thread.sleep(2000)
        printWithThreadName("channel close")
        channel.close()
    }
}