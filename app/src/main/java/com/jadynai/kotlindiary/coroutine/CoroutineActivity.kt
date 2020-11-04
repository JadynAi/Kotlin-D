package com.jadynai.kotlindiary.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 *JadynAi since 2020/10/9
 */
class CoroutineActivity : AppCompatActivity(), CoroutineScope by TestScope() {

    private val threadContext by lazy { FixThreadPool("test IO").asCoroutineDispatcher() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        textView2.click {
            run()
        }
        textView5.click {
            cancel()
        }
    }

    fun run() {
        launch {
            launch {
                val channel = Channel<Int>(Channel.UNLIMITED)
                // launch Handler 的Exception 一定要层层处理，否则还是会抛出来
                channel.invokeOnClose {
                    printWithThreadName("channel close")
                }
                launch {
                    while (isActive && !channel.isClosedForSend) {
                        withTimeout(2000) {
                            val c = channel.receive()
                            printWithThreadName("receive $c")
                        }
                    }
                }
                fakeRunData(channel)
            }
        }
    }

    private fun destroy() {
        cancel()
    }


    suspend fun <T> withTimeoutException(timeMillis: Long, block: suspend CoroutineScope.() -> T): T {
        try {
            return withTimeout(timeMillis, block)
        } catch (e: Exception) {
            throw Exception("time out")
        }
    }

    suspend fun fakeSendData(channel: Channel<Int>) = suspendCancellableCoroutine<Unit> { m ->
        thread {
            repeat(6) {
                if (it == 3) {
                    m.resumeWithException(Exception("klklklsdadasdasdadadasd"))
                    Thread.sleep(3000)
                } else {
                    Thread.sleep(500)
                }
                printWithThreadName("offer $it")
                channel.offer(it)
            }
//            m.resume(Unit)
        }
        m.invokeOnCancellation {
            printWithThreadName("asdasdadasd $isActive")
        }
    }

    fun fakeRunData(channel: Channel<Int>) {
        thread {
            repeat(6) {
                if (it == 3) {
                    Thread.sleep(3000)
                } else {
                    Thread.sleep(500)
                }
                printWithThreadName("offer $it")
                channel.offer(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}