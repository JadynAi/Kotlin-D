package com.jadynai.kotlindiary.coroutine

import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.function.ui.setVisible
import com.jadyn.kotlinp.coroutine.concurrent.Mutex0_0
import com.jadyn.kotlinp.coroutine.concurrent.Mutex0_1
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadynai.kotlindiary.R
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.invokeOnCompletion
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.sync.Semaphore
import java.nio.file.Path
import java.time.LocalDate
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *JadynAi since 2020/10/9
 */
@ExperimentalCoroutinesApi
class CoroutineActivity : AppCompatActivity(), CoroutineScope by TestScope() {

    var job: Job? = null

    private val semaphore by lazy { Semaphore(1) }

    private val defer: Deferred<PointF> = async(start = CoroutineStart.LAZY) {
        delay(3000)
        testAsynException()
    }

    private val scope1 = CoroutineScope(Dispatchers.IO )
    private val scope2 = CoroutineScope(Dispatchers.IO )

    private val hashMap = HashMap<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
//        var job = getNewJob()
        scope1.launch { testTwoScope() }
        textView2.click {
            scope1.cancel()
//            if (job.isCompleted) {
//                job = getNewJob()
//                Log.d("cecece", "onCreate: reset job")
//            } else {
//                Log.d("cecece", "onCreate: cancel")
//                job.cancel("haha", NullPointerException("cancel"))
//            }
//            Log.d("cece", "onCreate: click 2")
////            lifecycleScope.launch {
//            // 2021/5/19-16:30 协程的semaphore不会阻塞主线程
//            coroutine_progress.setVisible(true)
////                semaphore.acquire()
////                semaphore.release()
//            coroutine_progress.setVisible(false)
////            }
//            s.acquire()
//            s.release()
//            Log.d("cece", "onCreate: click 2 end")
//            launch {
//                val i = withContext(Dispatchers.IO) {
//                    repeat(3) {
//                        if (it == 1) {
//                            return@withContext it
//                        }
//                        Log.d("cecece", "onCreate: repeat $it")
//                    }
//                }
//            }
            Log.d("CoroutineActivity", "onCreate: ${defer.isActive} ${defer.isCompleted} ${defer.isCancelled}")
        }
        textView5.click {
            scope2.cancel()
//            Log.d("cece", "onCreate: click 5")
//            if (hashMap.containsKey(1000)) {
//                hashMap.remove(1000)
//            } else {
//                hashMap[1000] = "test1000"
//            }
//            lifecycleScope.coroutineContext.cancel()
//            lifecycleScope.launch {
//                val await = defer.await()
//                Log.d("CoroutineActivity", "onCreate: ${await} hascode ${await.hashCode()}")
//            }
        }
    }

    private suspend fun testTwoScope() {
        supervisorScope {
            // 2022/8/26-10:33 测试新scope是否会响应两个scope任意一个的cancel
            val newScope = this + scope2.coroutineContext
            val dd = arrayListOf<Deferred<Int>>()
            for (i in 0..100) {
                val deferred = newScope.async(start = CoroutineStart.LAZY) {
                    Log.d("cecece", "testTwoScope async start $i")
                    delay(10000)
                    Log.d("cecece", "testTwoScope async end $i")
                    i
                }
                dd.add(deferred)
            }
            try {
                dd.awaitAll()
                Log.d("cecece", "testTwoScope: finish")
            } catch (e: Exception) {
                Log.d("cecece", "testTwoScope: $e")
            }
        }
    }

    private fun getNewJob() = lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("cecece", "CoroutineExceptionHandler: $throwable")
    }) {
//        try {
        testAsyncCancel()
        while (true) {
            delay(3000)
            testAsyncCancel()
        }
//        } catch (e: Exception) {
//            Log.d("cecece", "getNewJob run catch: $e")
//        }
    }

    private suspend fun testAsyncCancel() {
        coroutineScope haha@{
            val task = this@haha.async(start = CoroutineStart.LAZY) { testAsynException() }
            task.invokeOnCompletion() {
                Log.d("cecece", "invokeOnCompletion: $it")
            }
//            try {
            task.await()
//            } catch (e: Exception) {
//                Log.d("cecece", "testAsyncCancel catch exception $e")
//            }
            // 2022/8/11-16:12 如果上面没有try catch,则这一行end的代码不会允许，一旦try catch
            // 或者try finally 则end的代码就会执行
            Log.d("cecece", "testAsyncCancel task await end")
        }
    }

    private suspend fun testAsynException() = suspendCancellableCoroutine<PointF> {
        it.invokeOnCancellation {
            Log.d("cecece", "testAsynException: ")
        }
        Schedulers.io().scheduleDirect {
            Thread.sleep(3000)
            it.resumeWithException(Exception("haha"))
        }
    }

    private suspend inline fun testCoroutineScope() = withContext(Dispatchers.IO) {
        withTimeoutOrNull(5000) {
            while (!hashMap.containsKey(1000)) {
                delay(100)
            }
            Log.d("cece", "testCoroutineScope: after hash map")
            hashMap[1000]
        }
    }

    private suspend fun go(coroutineScope: CoroutineScope) {
        printWithThreadName("go start")
        job?.cancelAndJoin()
        printWithThreadName("go end join")
        job = lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            printWithThreadName("inner job catch")
        }) {
            val test1 = test1()
            test1.awaitAll()
            printWithThreadName("end")
        }
        job?.invokeOnCompletion {
            printWithThreadName("complete ${it?.javaClass?.simpleName} ${it?.message ?: "NaN"}")
        }
    }

    suspend fun test1() = coroutineScope {
        val d = arrayListOf<Deferred<Int>>()
        for (i in 0..2) {
            val async = async(Dispatchers.IO) {
                printWithThreadName("async run start")
                delay(2000)
                if (i == 2) {
                    throw Exception("nothing on you")
                }
                printWithThreadName("async run end")
                i
            }
            d.add(async)
        }
        d
    }
}