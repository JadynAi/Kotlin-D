package com.jadyn.kotlinp.coroutine.channel

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 *JadynAi since 2020/9/19
 */
fun main() {
    Channel3().run()
}

@ExperimentalCoroutinesApi
class Channel3 : BaseMainTest() {

    override fun run() {
        launch {
            val produce = produce<Int>(capacity = Channel.UNLIMITED) {
                invokeOnClose {
                    // 正常运行完就不会接受到throwable，但是如果遇到异常，这个throwable就有值了
                    printWithThreadName("invoke channel close waibu ${it?.message} active ${isActive}")
                }
                // 这里面是一个全新的协程，请注意异步线程的挂起问题，否则channel会直接close掉
                try {
                    fakeRunData(this)
                    printWithThreadName("run after ${isActive}")
                } catch (e: Exception) {
                    printWithThreadName("catch channel run ${e.message}")
                }
            }
            printWithThreadName("start while ")
            while (isActive && !produce.isClosedForReceive) {
                // 如果使用receive就是try catch
                val v = withTimeout(2000) { produce.receiveOrNull() }
                printWithThreadName("receive $v")
            }
            printWithThreadName("run end over")
        }
    }

    suspend fun fakeRunData(channel: ProducerScope<Int>) = suspendCancellableCoroutine<Unit> { m ->
        thread {
            repeat(6) {
                if (it == 2) {
                    m.resumeWithException(Exception("adasdasda"))
                    Thread.sleep(1000)
                } else {
                    Thread.sleep(500)
                }
//                printWithThreadName("offer $it")
                if (!channel.isClosedForSend) {
                    channel.offer(it)
                }
            }
            if (!m.isCompleted) {
                m.resume(Unit)
            }
        }
        m.invokeOnCancellation {
            printWithThreadName("suspend invoke cancel")
        }
    }

}