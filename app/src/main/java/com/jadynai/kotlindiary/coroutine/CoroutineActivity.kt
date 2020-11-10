package com.jadynai.kotlindiary.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.utils.getReal
import com.jadyn.kotlinp.coroutine.channel.receive
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadyn.kotlinp.coroutine.singleExecutors
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 *JadynAi since 2020/10/9
 */
@ExperimentalCoroutinesApi
class CoroutineActivity : AppCompatActivity(), CoroutineScope by TestScope() {

    private val threadContext by lazy { FixThreadPool("test IO").asCoroutineDispatcher() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        textView2.click {
            launch {
                withContext(singleExecutors.asCoroutineDispatcher()) {
                    async {
                        testSuspend()
                    }.await()
                }
            }
        }
        textView5.click {
            cancel()
        }
    }

    suspend fun testSuspend() = suspendCancellableCoroutine<Unit> {
        it.invokeOnCancellation {
            printWithThreadName("invoke cancel")
        }
        it.resume(Unit)
        thread {
            repeat(20) {
                Thread.sleep(300)
            }
        }
    }

    fun runActor() {
        launch {
            val receive = receive<Int>(Channel.UNLIMITED) {
                receiveAsFlow().collect {
                    printWithThreadName("receive collect $it")
                }
            }
            printWithThreadName("start with context")
            thread {
                repeat(10) {
                    Thread.sleep(500)
                    printWithThreadName("thread offer $it")
                    receive.offer(it)
                }
            }
        }
    }

    fun run() {
        launch {
            val produce = produce<Int>(capacity = Channel.UNLIMITED) {
                invokeOnClose {
                    // 正常运行完就不会接受到throwable，但是如果遇到异常，这个throwable就有值了
                    printWithThreadName("invoke channel close waibu ${it?.message.getReal("Nau")} active ${isActive}")
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

    override fun onDestroy() {
        super.onDestroy()
        cancel()
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
}