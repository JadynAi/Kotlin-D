package com.jadynai.kotlindiary.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

/**
 *JadynAi since 2020/10/9
 */
class CoroutineActivity : AppCompatActivity(), CoroutineScope by TestScope() {

    private val threadContext by lazy { FixThreadPool("test IO").asCoroutineDispatcher() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        textView2.click {
            launch {
                println("start")
                val channel = Channel<Float>()
                launch {
                    channel.receiveAsFlow().collect {
//                        printWithThreadName("collect $it")
                    }
                }
//                supervisorScope {
//                    repeat(7) {
//                        // CoroutineExceptionHandler不能应用于async,cancel 的话在这里cancel会取消掉
//                        try {
//                            async(Dispatchers.IO) {
//                                testC(it, channel)
//                            }.await()
//                        } catch (e: Exception) {
//                            printWithThreadName("catch exception ${e.message}")
//                        }
//                    }
//                }
                repeat(7) {
                    // CoroutineExceptionHandler不能应用于async,cancel 的话在这里cancel会取消掉
                    try {
                        withContext(threadContext) {
                            testC(it, channel)
                        }
                    } catch (e: Exception) {
                        printWithThreadName("catch exception ${e.message}")
                    }
                }
                // 确保channel close掉，才会执行到这里
                println("end")
            }
        }
        textView5.click {
            cancel()
        }
    }

    suspend fun testC(num: Int, emitter: Channel<Float>): Int {
        if (num == 2) {
            throw IllegalStateException("exception!!!")
        }
        printWithThreadName("test ccc go $num")
        repeat(5) {
            delay(200)
            emitter.send(it.toFloat())
        }
//        if (num == 4) {
//            // 使用完要及时关闭，否则supervisor没有追踪到end，不会允许到end哪里
//            emitter.close()
//        }
        return num
    }
}