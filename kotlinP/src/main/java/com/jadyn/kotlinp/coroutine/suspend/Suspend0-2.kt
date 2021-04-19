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
    SuspendCoroutineScopeTest().run()
}

class SuspendCoroutineScopeTest : BaseMainTest() {

    private var count = 0

    private var isSpecialFinished = false

    private val specialJob by lazy {
        launch(Dispatchers.IO) {
            printWithThreadNameAndTime("special start")
            delay(1000)
            count++
            isSpecialFinished = true
        }
    }

    override fun run() {
        repeat(5) {
            printWithThreadName("repeat $it")
            launch {
                printWithThreadNameAndTime("repeat $it")
                if (!isSpecialFinished) {
                    specialJob.join()
                }
                printWithThreadNameAndTime("join special ended $it : $count")
//                test111()
            }
        }
    }

    private suspend fun test111() {
        coroutineScope {
            launch(Dispatchers.IO) { delay(3000) }.join()
            printWithThreadNameAndTime("test111 scope end")
        }
        printWithThreadNameAndTime("test111 end")
    }
}