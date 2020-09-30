package com.jadyn.kotlinp.coroutine.error

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import java.lang.IllegalStateException

/**
 *JadynAi since 2020/9/28
 */
fun main() {
    HandleErrorTest().run()
}

abstract class BaseHandleErrorTest : BaseMainTest() {
    override fun run() {
        testSuspend()
//        testAsync()
//        testSupervisor()
//        testCoroutineScope()
    }

    private fun testSupervisor() {
        launch {
            println("start")
            val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                printWithThreadName("throwable ${throwable.message}")
            }
            supervisorScope {
                repeat(5) {
                    flowOf(0f)
                    // CoroutineExceptionHandler不能应用于async
                    launch(Dispatchers.IO + coroutineExceptionHandler) {
                        test1(it)
                    }.join()
                }
            }
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
                try {
                    // withContext不算启动一个协程，所以withContext加上coroutineExceptionHandler不起作用
                    runSingle(it)
                } catch (e: Exception) {
                    printWithThreadName("catch exception ${e.message}")
                }
            }
        }
    }

    suspend fun runSingle(num: Int) = withContext(Dispatchers.IO) {
        test1(num)
    }

    abstract suspend fun test1(it: Int)

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

}

