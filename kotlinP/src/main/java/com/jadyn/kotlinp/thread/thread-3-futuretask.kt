package com.jadyn.kotlinp.thread

import com.jadyn.kotlinp.coroutine.printWithThreadNameAndTime
import java.util.concurrent.FutureTask

/**
 *JadynAi since 5/19/21
 */
fun main() {
    printWithThreadNameAndTime("start")
    val i = test().get()
    printWithThreadNameAndTime("end $i")
}

private fun test(): FutureTask<Int> {
    val futureTask = FutureTask<Int>({
        Thread.sleep(3000)
    }, 1)
    Thread(futureTask).start()
    return futureTask
}