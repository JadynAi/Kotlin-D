package com.jadyn.kotlinp.coroutine.error

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadNameAndTime
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

/**
 *JadynAi since 2020/9/29
 */
fun main() {
    CancelTest().run()
}

class CancelTest : BaseMainTest() {

    override fun run() {
        launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            printWithThreadNameAndTime("handle $throwable")
        }) {
            for (i in 0..5) {
                withContext(Dispatchers.IO) { test2() }
            }
            printWithThreadNameAndTime("end")
        }
    }

    private suspend fun test2() = suspendCoroutine<Unit> {
        printWithThreadNameAndTime("test start")
        Thread.sleep(200)
        printWithThreadNameAndTime("test happen")
        Random(5).nextDouble()
//        it.resumeWithException(Exception("failed"))
            it.resume(Unit)
    }
}