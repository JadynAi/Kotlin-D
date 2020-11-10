package com.jadyn.kotlinp.coroutine.channel

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.coroutines.resume

/**
 *JadynAi since 2020/9/19
 */
fun main() {
    Channel2().run()
}

class Channel2 : BaseMainTest() {

    override fun run() {
        // actor被废弃了，总得有个替代的吧……
        val actor = actor<Int> { }
        launch {
            receive<Int> {
                receiveAsFlow().collect { }
            }
        }
    }
}

fun <E> CoroutineScope.receive(capacity: Int = 0, block: suspend ReceiveChannel<E>.() -> Unit): SendChannel<E> {
    val channel = Channel<E>(capacity)
    launch {
        printWithThreadName("receive launch start")
        invokeChannelReceive(channel)
        launch { block.invoke(channel) }
        printWithThreadName("receive launch end")
    }
    printWithThreadName("receive return ")
    return channel
}

suspend fun <E> invokeChannelReceive(channel: Channel<E>) = suspendCancellableCoroutine<Unit> {
    it.invokeOnCancellation {
        printWithThreadName("catch invoke cancel")
        channel.close()
    }
    it.resume(Unit)
}

