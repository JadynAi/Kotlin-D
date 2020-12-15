package com.jadynai.kotlindiary.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.kotlinp.coroutine.concurrent.Mutex0_0
import com.jadyn.kotlinp.coroutine.concurrent.Mutex0_1
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*

/**
 *JadynAi since 2020/10/9
 */
@ExperimentalCoroutinesApi
class CoroutineActivity : AppCompatActivity(), CoroutineScope by TestScope() {

    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        textView2.click {
            lifecycleScope.launch {
                testCoroutinScope()
            }
        }
        textView5.click {
            lifecycleScope.launch {
                job?.join()
                Log.d("cece", "onCreate: end")
            }
        }
    }

    suspend fun testCoroutinScope() {
        job?.cancelAndJoin()
        job = coroutineScope {
            launch(Dispatchers.IO) {
                Log.d("cece", "testCoroutinScope: start")
                delay(3000)
            }
        }
        job!!.join()
        Log.d("cece", "testCoroutinScope: join end")
    }

    suspend fun go(coroutineScope: CoroutineScope) {
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