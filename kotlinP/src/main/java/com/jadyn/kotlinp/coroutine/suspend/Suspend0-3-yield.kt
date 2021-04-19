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
 *
 * 测试yield
 */
fun main() {
    SuspendCoroutineYieldTest().run()
}

class SuspendCoroutineYieldTest : BaseMainTest() {

    private val single = singleExecutors.asCoroutineDispatcher()

    override fun run() {
        launch {
            test2()
        }
    }

    private suspend fun test2() {
        launch {
            repeat(3) {
                println("job1 is $it")
                yield()
            }
        }
        launch {
            repeat(3) {
                println("job2 is $it")
            }
        }
    }

    private suspend fun test1() {
        val sequence = sequence<Int> {
            yield(1)
            var cur = 1
            var next = 1
            while (true) {
                yield(next)
                val tmp = cur + next
                cur = next
                next = tmp
            }
        }
        sequence.take(5).forEach {
            println("sequence for $it")
        }
    }

}