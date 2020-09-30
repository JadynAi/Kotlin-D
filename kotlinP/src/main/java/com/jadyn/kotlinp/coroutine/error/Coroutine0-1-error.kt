package com.jadyn.kotlinp.coroutine.error

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.mainExecutors
import com.jadyn.kotlinp.coroutine.printWithThreadName
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.lang.IllegalStateException
import kotlin.Exception
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 *JadynAi since 2020/9/28
 */
fun main() {
    HandleErrorTest().run()
}

abstract class BaseHandleErrorTest : BaseMainTest() {
    override fun run() {
//        testSuspend()
//        testAsync()
        testSupervisor()
//        testCoroutineScope()
    }

    private fun testSupervisor() {
        launch {
            println("start")
            supervisorScope {
                val channel = Channel<Float>()
                launch {
                    channel.receiveAsFlow().collect {
//                        printWithThreadName("collect $it")
                    }
                }
                repeat(5) {
                    // CoroutineExceptionHandler不能应用于async,cancel 的话在这里cancel会取消掉
//                    if (it == 2) {
//                        cancel()
//                    }
                    try {
                        async(Dispatchers.IO) {
                            testC(it, channel)
                        }.await()
                    } catch (e: Exception) {
                        printWithThreadName("catch exception ${e.message}")
                    }
                }
            }
            // 确保channel close掉，才会执行到这里
            println("end")
        }
    }

    private fun testCoroutineScope() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            printWithThreadName("throwable ${throwable.message}")
        }
        // 在外部统一处理异常
        launch(coroutineExceptionHandler) {
            println("start")
            // coroutineScope对异常的处理是"一荣俱荣一陨俱陨"，所以使用coroutineScope必须在外部处理异常
            coroutineScope {
                repeat(5) {
                    flowOf(0f)
                    // CoroutineExceptionHandler不能应用于async
                    // 外部使用了coroutineScope来追踪的话，不能在子协程使用Handler处理错误，否则还是会抛出异常
                    launch(Dispatchers.IO) {
                        test1(it)
                    }
                }
            }
            println("end")
        }
    }

    private fun testAsync() {
        launch {
            // 不使用supervisorScope的话，async的异常还是会被抛出
            supervisorScope {
                repeat(5) {
                    val async = async(Dispatchers.IO) {
                        test1(it)
                    }
                    try {
                        // join 不会catch异常
//                        async.join()
                        async.join()
                    } catch (e: Exception) {
                        printWithThreadName("async catch exception ${e.message}")
                    }
                }
            }
        }
    }

    private fun testSuspend() {
        launch {
            repeat(5) {
                // withContext不算启动一个协程，所以withContext加上coroutineExceptionHandler不起作用
                try {
                    flow<Float> {
                        val result = withContext(Dispatchers.IO) {
                            if (it == 3) {
                                cancel()
                            }
                            test2(it, this@flow)
                        }
                        printWithThreadName("test result finish $result")
                    }.flowOn(Dispatchers.IO).collect { printWithThreadName("collect $it") }
                } catch (e: Exception) {
                    printWithThreadName("catch exception ${e.message}")
                }
            }
        }
    }

    abstract suspend fun test1(it: Int)

    open suspend fun test2(num: Int, emitter: FlowCollector<Float>? = null): Int {
        return -1
    }

    open suspend fun testC(num: Int, emitter: Channel<Float>): Int {
        return -1
    }
}

class HandleErrorTest : BaseHandleErrorTest() {
    override suspend fun test1(it: Int) {
        printWithThreadName("test $it start")
        delay(500)
        if (it == 1) {
            throw IllegalStateException("ha ha one")
        }
        printWithThreadName("test $it finish")
    }

    override suspend fun test2(num: Int, emitter: FlowCollector<Float>?): Int {
        if (num == 1) {
            throw IllegalStateException("exception!!!")
        }
        printWithThreadName("test2 go $num")
        repeat(5) {
            delay(100)
            emitter?.emit(it.toFloat())
        }
        return num + 1
    }

    override suspend fun testC(num: Int, emitter: Channel<Float>): Int {
        if (num == 2) {
            throw IllegalStateException("exception!!!")
        }
        printWithThreadName("test ccc go $num")
        repeat(5) {
            delay(100)
            emitter.send(it.toFloat())
        }
        if (num == 4) {
            // 使用完要及时关闭，否则supervisor没有追踪到end，不会允许到end哪里
            emitter.close()
        }
        return num
    }
}

