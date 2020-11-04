package com.jadyn.kotlinp.coroutine.channel

import android.os.CountDownTimer
import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onReceiveOrNull
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.TimeoutException
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

/**
 *JadynAi since 2020/9/19
 */
fun main() {
    Channel0TimeOutTest().run()
}

class Channel0TimeOutTest : BaseMainTest() {

    override fun run() {
        val channel = Channel<Int>(Channel.UNLIMITED)
        // launch Handler 的Exception 一定要层层处理，否则还是会抛出来
       launch(CoroutineExceptionHandler { coroutineContext, throwable ->
       }) {
           launch(CoroutineExceptionHandler { coroutineContext, throwable ->
               // handler 会忽视CancellationException,而timeOut 抛出的就是TimeoutCancellationException，继承自会忽视CancellationException
               printWithThreadName("catch exception ${throwable.message}")
           }) {
               while (!channel.isClosedForSend) {
                   withTimeoutException(2000) {
                       val c = channel.receive()
                       printWithThreadName("receive $c")
                   }
               }
           }
           launch {
               repeat(6) {
                   if (it == 2) {
                       delay(3000)
                   } else {
                       delay(500)
                   }
                   channel.offer(it)
               }
           }
       }
    }
    
    
    suspend fun <T> withTimeoutException(timeMillis: Long, block: suspend CoroutineScope.() -> T): T {
        return try {
            withTimeout(timeMillis, block)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}
