package com.jadynai.kotlindiary.coroutine

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
import kotlinx.coroutines.sync.Semaphore
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resumeWithException

/**
 *JadynAi since 2020/10/9
 */
@ExperimentalCoroutinesApi
class CoroutineActivity : AppCompatActivity(), CoroutineScope by TestScope() {

    var job: Job? = null

    private val semaphore by lazy { Semaphore(1) }

    private val asyncTest by lazy {
        async(start = CoroutineStart.LAZY) {
            semaphore.acquire()
            delay(10000)
            semaphore.release()
            "wait end"
        }
    }

    private val defer: Deferred<String> = async(start = CoroutineStart.LAZY) {
        delay(3000)
        try {
            testAsynException()
        } catch (e: Throwable) {
            // 2021/6/30-10:25 抛出throwable就要catch throwable，但是throwable一般不要这么catch
            "sdadasdasda"
        }
    }

    private val hashMap = HashMap<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        val s = java.util.concurrent.Semaphore(1)
        Thread {
            s.acquire()
            Thread.sleep(10000)
            s.release()
        }.start()
        textView2.click {
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
            Log.d("CoroutineActivity", "onCreate: ${defer.isActive} ${defer.isCompleted} ${defer.isCancelled}")
        }
        textView5.click {
//            Log.d("cece", "onCreate: click 5")
//            if (hashMap.containsKey(1000)) {
//                hashMap.remove(1000)
//            } else {
//                hashMap[1000] = "test1000"
//            }
//            lifecycleScope.coroutineContext.cancel()
            lifecycleScope.launch {
                val await = defer.await()
                Log.d("CoroutineActivity", "onCreate: ${await}")
            }
        }
    }

    private suspend fun testAsynException() = suspendCancellableCoroutine<String> {
        it.invokeOnCancellation { }
        Schedulers.io().scheduleDirect {
            Thread.sleep(3000)
            it.resumeWithException(Throwable("asdasdasdad"))
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